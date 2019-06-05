package com.example.lost;

import android.Manifest;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LogPrinter;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collection;


public class MainActivity extends AppCompatActivity implements Scene.OnUpdateListener {

    private static final String TAG = "file" ;
    private ArFragment arFragment;
    private ModelRenderable redCubeRenderable;
    private Context context;

    //detection
    private ArSceneView arView;
    private Session session;
    private boolean shouldCofigureSession = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = getBaseContext();
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.detection);

        /*
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.ux_fragment);


        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material-> {
                            redCubeRenderable =
                                    ShapeFactory.makeCube(new Vector3(0.1f,0.1f,0.1f), new Vector3(0.0f,0.0f,0.0f), material);
                        }
                        );


        //si vous rencontrez un problème ici, il est possible que vous ne soyez pas en java 8 (nécessaire pour les lambdas)



        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (redCubeRenderable == null) {
                        return;
                    }
                    if (plane.getType() != Plane.Type.HORIZONTAL_UPWARD_FACING) {
                        return;
                    }
                    //On créé le point d'encrage du modèle 3d
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    //On attache ensuite notre modèle au point d'encrage
                    TransformableNode Node = new TransformableNode(arFragment.getTransformationSystem());
                    Node.setParent(anchorNode);
                    Node.setRenderable(redCubeRenderable);
                    Node.select();
                }
        );*/

        arView = (ArSceneView)findViewById(R.id.arView);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        setupSession();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this,"Permission need to display camera",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        initSceneView();

    }

    private void initSceneView() {
        arView.getScene().addOnUpdateListener(this);
    }

    private void setupSession() {
        if (session == null){
            try {
                session = new Session(this);
            } catch (UnavailableArcoreNotInstalledException e) {
                e.printStackTrace();
            } catch (UnavailableApkTooOldException e) {
                e.printStackTrace();
            } catch (UnavailableSdkTooOldException e) {
                e.printStackTrace();
            } catch (UnavailableDeviceNotCompatibleException e) {
                e.printStackTrace();
            }
            shouldCofigureSession = true;
        }
        if (shouldCofigureSession){
            configSession();
            shouldCofigureSession = false;
            arView.setupSession(session);
        }
        try {
            session.resume();
            arView.resume();
        } catch (CameraNotAvailableException e) {
            e.printStackTrace();
            session = null;
            return;
        }
    }

    private void configSession() {
        Config config = new Config(session);
        if (!buildDatabase(config)){
            Toast.makeText(this,"Error database",Toast.LENGTH_SHORT).show();

        }
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        session.configure(config);
    }

    private boolean buildDatabase(Config config) {
        AugmentedImageDatabase augmentedImageDatabase;
        Bitmap bitmap = loadImage();
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase = new AugmentedImageDatabase(session);
        augmentedImageDatabase.addImage("pc3",bitmap);

        config.setAugmentedImageDatabase(augmentedImageDatabase);
        return true;
    }

    private Bitmap loadImage() {
        InputStream is = null;
        try {

            is = getAssets().open("frame.png");
            Toast.makeText(MainActivity.this,"image trouvée",Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();

           // Toast.makeText(MainActivity.this,"image indisponible",Toast.LENGTH_SHORT).show();
        }
        return BitmapFactory.decodeStream(is);
    }

    public void onUpdate(FrameTime frameTime){
        Frame frame = arView.getArFrame();
        Collection<AugmentedImage> updateAugmentedimage = frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage image : updateAugmentedimage){
            if (image.getTrackingState() == TrackingState.TRACKING){
                if (image.getName().equals("pc3")){

                    MyArNode node = new MyArNode(this, R.raw.lion);
                    node.setImage(image);

                    arView.getScene().addChild(node);
                }
            }
        }


    }

    protected void onResume(){
        super.onResume();
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        setupSession();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this,"Permission need to display camera",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    protected void onPause(){
        super.onPause();
        if (session != null){
            arView.pause();
            session.pause();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
