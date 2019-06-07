package com.example.lost;

import android.Manifest;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.TouchEventSystem;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    private Button refreshBtn;
    private boolean hasNode;
    private MyArNode node;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = getBaseContext();
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.detection);
        this.hasNode=false;
        this.node = null;


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
        augmentedImageDatabase = new AugmentedImageDatabase(session);
        String[] codes;
        String path = "pictures";

        String [] list;
        try {
            list = getAssets().list(path);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    Log.d("fileInfo", file);

                    Bitmap bitmap = loadImage(path+"/"+file);
                    if (bitmap == null){
                        return false;
                    }

                    augmentedImageDatabase.addImage(file,bitmap);

                }
            }
        } catch (IOException e) {
            return false;
        }



/*
        Bitmap bitmap = loadImage("pictures/pc3.png");
        if (bitmap == null){
            return false;
        }

        augmentedImageDatabase.addImage("pc3",bitmap);


        bitmap = loadImage("pictures/pc1.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("pc1",bitmap);
*/




        config.setAugmentedImageDatabase(augmentedImageDatabase);
        return true;
    }

    private Bitmap loadImage(String path) {
        InputStream is = null;
        try {

            is = getAssets().open(path);
            //Toast.makeText(MainActivity.this,"image trouvée",Toast.LENGTH_SHORT).show();


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
            if (image.getTrackingState() == TrackingState.TRACKING  ){

                if (image.getName().equals("pc3.png") ){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.lion);
                        hasNode = true;
                    }
                    else {
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.lion);
                    }
                    node.setImage(image);
                    arView.getScene().addChild(node);


                    //renvoyer position
                }
                else if (image.getName().equals("pc1.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.dino);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.dino);

                    }
                    node = new MyArNode(this, R.raw.lion);
                }

                else if (image.getName().equals("Ambs.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.pierreambs);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.pierreambs);

                    }
                    node = new MyArNode(this, R.raw.pierreambs);
                }

                else if (image.getName().equals("annexe_sanitaire.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.annexesanitaire);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.annexesanitaire);

                    }
                    node = new MyArNode(this, R.raw.annexesanitaire);
                }

                else if (image.getName().equals("Aubry.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.evelyneaubry);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.evelyneaubry);

                    }
                    node = new MyArNode(this, R.raw.evelyneaubry);
                }

                else if (image.getName().equals("Basset.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.michelbasset);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.michelbasset);

                    }
                    node = new MyArNode(this, R.raw.michelbasset);
                }

                else if (image.getName().equals("Ben_Souissi.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.souhirbensouissi);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.souhirbensouissi);

                    }
                    node = new MyArNode(this, R.raw.souhirbensouissi);
                }

                else if (image.getName().equals("Binder.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.gerardbinder);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.gerardbinder);

                    }
                    node = new MyArNode(this, R.raw.gerardbinder);
                }

                else if (image.getName().equals("Birouche-Mourllion.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.abderazikbirouchebenjaminmourllion);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.abderazikbirouchebenjaminmourllion);

                    }
                    node = new MyArNode(this, R.raw.abderazikbirouchebenjaminmourllion);
                }

                else if (image.getName().equals("bureau_chercheur_LSI.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.bureauchercheurslsi);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.bureauchercheurslsi);

                    }
                    node = new MyArNode(this, R.raw.bureauchercheurslsi);
                }

                else if (image.getName().equals("bureau_chercheur_MIAM_1.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.bureauchercheursmiam1);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.bureauchercheursmiam1);

                    }
                    node = new MyArNode(this, R.raw.bureauchercheursmiam1);
                }

                else if (image.getName().equals("bureau_chercheur_MIAM_2.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.bureauchercheursmiam2);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.bureauchercheursmiam2);

                    }
                    node = new MyArNode(this, R.raw.bureauchercheursmiam2);
                }

                else if (image.getName().equals("bureau_chercheur_MIAM_3.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.bureauchercheursmiam3);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.bureauchercheursmiam3);

                    }
                    node = new MyArNode(this, R.raw.bureauchercheursmiam3);
                }

                else if (image.getName().equals("Dupuis.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.raphaeldupuis);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.raphaeldupuis);

                    }
                    node = new MyArNode(this, R.raw.raphaeldupuis);
                }

                else if (image.getName().equals("E30.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.e30);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.e30);

                    }
                    node = new MyArNode(this, R.raw.e30);
                }

                else if (image.getName().equals("E31.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.e31);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.e31);

                    }
                    node = new MyArNode(this, R.raw.e31);
                }

                else if (image.getName().equals("E32.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.e32);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.e32);

                    }
                    node = new MyArNode(this, R.raw.e32);
                }

                else if (image.getName().equals("E33.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.e33);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.e33);

                    }
                    node = new MyArNode(this, R.raw.e33);
                }

                else if (image.getName().equals("E34.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.e34);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.e34);

                    }
                    node = new MyArNode(this, R.raw.e34);
                }

                else if (image.getName().equals("E36.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.e36mef);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.e36mef);

                    }
                    node = new MyArNode(this, R.raw.e36mef);
                }

                else if (image.getName().equals("E37.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.e37salleinfomiage1);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.e37salleinfomiage1);

                    }
                    node = new MyArNode(this, R.raw.e37salleinfomiage1);
                }

                else if (image.getName().equals("E37-bis.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.e37bis);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.e37bis);

                    }
                    node = new MyArNode(this, R.raw.e37bis);
                }

                else if (image.getName().equals("E38.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.e38salleinfomiage2);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.e38salleinfomiage2);

                    }
                    node = new MyArNode(this, R.raw.e38salleinfomiage2);
                }

                else if (image.getName().equals("Fondement.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.fredericfondement);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.fredericfondement);

                    }
                    node = new MyArNode(this, R.raw.fredericfondement);
                }

                else if (image.getName().equals("Forestier.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.germainforestier);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.germainforestier);

                    }
                    node = new MyArNode(this, R.raw.germainforestier);
                }

                else if (image.getName().equals("Hassenforder.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.michelhassenforder);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.michelhassenforder);

                    }
                    node = new MyArNode(this, R.raw.michelhassenforder);
                }

                else if (image.getName().equals("IARISS.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.iariss);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.iariss);

                    }
                    node = new MyArNode(this, R.raw.iariss);
                }

                else if (image.getName().equals("labo_lsi.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.laboratoirelsi);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.laboratoirelsi);

                    }
                    node = new MyArNode(this, R.raw.laboratoirelsi);
                }

                else if (image.getName().equals("Lauffenburger.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.jeanphillipelauffenburger);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.jeanphillipelauffenburger);

                    }
                    node = new MyArNode(this, R.raw.jeanphillipelauffenburger);
                }

                else if (image.getName().equals("Laurain.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.thomaslaurent);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.thomaslaurent);

                    }
                    node = new MyArNode(this, R.raw.thomaslaurent);
                }

                else if (image.getName().equals("Ledy.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.jonathanledy);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.jonathanledy);

                    }
                    node = new MyArNode(this, R.raw.jonathanledy);
                }

                else if (image.getName().equals("LSI.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.lsi);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.lsi);

                    }
                    node = new MyArNode(this, R.raw.lsi);
                }

                else if (image.getName().equals("Muller.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.pierrealainmuller);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.pierrealainmuller);

                    }
                    node = new MyArNode(this, R.raw.pierrealainmuller);
                }

                else if (image.getName().equals("Orjuela.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.rodolfoorjuela);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.rodolfoorjuela);

                    }
                    node = new MyArNode(this, R.raw.rodolfoorjuela);
                }

                else if (image.getName().equals("PC_reseaux.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.laborechercheprojets);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.laborechercheprojets);

                    }
                    node = new MyArNode(this, R.raw.laborechercheprojets);
                }

                else if (image.getName().equals("Perronne.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.jeanmarcperronne);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.jeanmarcperronne);

                    }
                    node = new MyArNode(this, R.raw.jeanmarcperronne);
                }

                else if (image.getName().equals("Pinot.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.gilbertpinot);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.gilbertpinot);

                    }
                    node = new MyArNode(this, R.raw.gilbertpinot);
                }

                else if (image.getName().equals("prof_invite.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.professeurinvite);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.professeurinvite);

                    }
                    node = new MyArNode(this, R.raw.professeurinvite);
                }

                else if (image.getName().equals("secretariat_miage.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.patriciabontesecretariatmiage);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.patriciabontesecretariatmiage);

                    }
                    node = new MyArNode(this, R.raw.patriciabontesecretariatmiage);
                }

                else if (image.getName().equals("Studer.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.philippestuder);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.philippestuder);

                    }
                    node = new MyArNode(this, R.raw.philippestuder);
                }

                else if (image.getName().equals("tableau_sectoriel.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.tableausectoriel);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.tableausectoriel);

                    }
                    node = new MyArNode(this, R.raw.tableausectoriel);
                }

                else if (image.getName().equals("tableau_sectoriel_ts8.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.tableausectoriel);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.tableausectoriel);

                    }
                    node = new MyArNode(this, R.raw.tableausectoriel);
                }

                else if (image.getName().equals("Thiry.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.laurentthiry);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.laurentthiry);

                    }
                    node = new MyArNode(this, R.raw.laurentthiry);
                }

                else if (image.getName().equals("toilettes_femmes.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.toilettesfemmes);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.toilettesfemmes);

                    }
                    node = new MyArNode(this, R.raw.toilettesfemmes);
                }

                else if (image.getName().equals("toilettes_hommes.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.toiletteshommes);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.toiletteshommes);

                    }
                    node = new MyArNode(this, R.raw.toiletteshommes);
                }

                else if (image.getName().equals("toilettes_handicapées.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.toilettes);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.toilettes);

                    }
                    node = new MyArNode(this, R.raw.toilettes);
                }

                else if (image.getName().equals("toilettes_ts7.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.toilettes);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.toilettes);

                    }
                    node = new MyArNode(this, R.raw.toilettes);
                }

                else if (image.getName().equals("vestiaire.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.vestiaires);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.vestiaires);

                    }
                    node = new MyArNode(this, R.raw.vestiaires);
                }

                else if (image.getName().equals("Weber.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.jonathanweber);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.jonathanweber);

                    }
                    node = new MyArNode(this, R.raw.jonathanweber);
                }

                else if (image.getName().equals("Weisser.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.thomasweisser);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.thomasweisser);

                    }
                    node = new MyArNode(this, R.raw.thomasweisser);
                }


                /*
                else if (image.getName().equals("E34.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.e34);
                        node.setImage(image);

                        arView.getScene().addChild(node);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        //node = new MyArNode(this, R.raw.lion);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.e34);
                        node.setImage(image);
                        arView.getScene().addChild(node);
                    }
                    //arView.getScene().removeChild(node);


                    //node = new MyArNode(this, R.raw.e34);

                    //node.setImage(image);
                    //arView.getScene().addChild(node);

                }
            */




            }
            else{
                arView.invalidate();
                node = new MyArNode();

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
