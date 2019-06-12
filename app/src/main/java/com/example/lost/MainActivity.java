package com.example.lost;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lost.MyArNode;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Scene.OnUpdateListener {


    private Context context;

    //detection
    private ArSceneView arView;
    private Session session;
    private boolean shouldCofigureSession = false;

    private boolean hasNode;
    private MyArNode node;


    private Animator currentAnimator;
    private int shortAnimationDuration;
    private boolean hasNode2;
    private MyArNode node2;
    private boolean planVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = getBaseContext();
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.detection);
        this.hasNode=false;
        this.node = null;
        this.hasNode2=false;
        this.node2 = null;
        this.planVisible = false;



        final View thumb1View = findViewById(R.id.thumb_button_1);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //thumb1View.setEnabled(true);
                //ImageView plan = findViewById(R.id.expanded_image);

                //plan.setVisibility(View.VISIBLE);
                View container = findViewById(R.id.planLayout);
                //container.setVisibility(View.VISIBLE);
                if(!planVisible)
                    container.setVisibility(View.VISIBLE);
                else
                    container.setVisibility(View.INVISIBLE);

                zoomImageFromThumb(thumb1View, R.drawable.plan);

            }
        });

        // Retrieve and cache the system's default "short" animation time.
        shortAnimationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);


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
        else {
            Toast.makeText(this,"Database has been created",Toast.LENGTH_SHORT).show();
        }
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        session.configure(config);
    }

    private boolean buildDatabase(Config config) {
        /*
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
        */
        List<String> list = new ArrayList<String>(100);
        list.add("Ambs.png");
        list.add("annexe_sanitaire.png");
        list.add("Aubry.png");
        list.add("Basset.png");
        list.add("Ben_Souissi.png");
        list.add("Binder.png");
        list.add("Birouche-Mourllion.png");
        list.add("bureau_chercheur_LSI.png");
        list.add("bureau_chercheur_MIAM_1.png");
        list.add("bureau_chercheur_MIAM_2.png");
        list.add("bureau_chercheur_MIAM_3.png");
        list.add("cafe.png");
        list.add("Dupuis.png");
        list.add("E30.png");
        list.add("E31.png");
        list.add("E32.png");
        list.add("E33.png");
        list.add("E34.png");
        list.add("E35.png");
        list.add("E36.png");
        list.add("E37.png");
        list.add("E37-bis.png");
        list.add("E38.png");
        //list.add("earth.jpg");
        list.add("Fondement.png");
        list.add("Forestier.png");
        //list.add("green.png");
        list.add("Hassenforder.png");
        list.add("IARISS.png");
        list.add("labo_lsi.png");
        list.add("Lauffenburger.png");
        list.add("Laurain.png");
        list.add("Ledy.png");
        list.add("LSI.png");
        list.add("Muller.png");
        list.add("Orjuela.png");
        list.add("pc1.png");
        list.add("pc3.png");
        list.add("PC_reseaux.png");
        list.add("Perronne.png");
        list.add("Pinot.png");
        list.add("prof_invite.png");
        //list.add("red.png");
        list.add("salle_reunion.png");
        list.add("secretariat_miage.png");
        list.add("Studer.png");
        list.add("tableau_sectoriel.png");
        list.add("tableau_sectoriel_ts8.png");
        list.add("Thiry.png");
        list.add("toilettes_femmes.png");
        list.add("toilettes_handicapées.png");
        list.add("toilettes_hommes.png");
        list.add("toilettes_ts7.png");
        list.add("vestiaire.png");
        list.add("Weber.png");
        list.add("Weisser.png");

        AugmentedImageDatabase augmentedImageDatabase;
        augmentedImageDatabase = new AugmentedImageDatabase(session);
        Bitmap bitmap;
        String s;
        /*
        for (Iterator<String> i = list.iterator(); ((Iterator) i).hasNext();){
            bitmap=null;
            s = i.next();
            bitmap=loadImage(s);
            if (bitmap == null){
                return false;
            }
            augmentedImageDatabase.addImage(s,bitmap);
        }
        */
        augmentedImageDatabase = new AugmentedImageDatabase(session);




        bitmap = null;
        bitmap = loadImage("Ambs.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Ambs.png",bitmap);


        bitmap = null;
        bitmap = loadImage("annexe_sanitaire.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("annexe_sanitaire.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Aubry.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Aubry.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Basset.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Basset.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Ben_Souissi.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Ben_Souissi.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Binder.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Binder.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Birouche-Mourllion.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Birouche-Mourllion.png",bitmap);


        bitmap = null;
        bitmap = loadImage("bureau_chercheur_LSI.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("bureau_chercheur_LSI.png",bitmap);


        bitmap = null;
        bitmap = loadImage("bureau_chercheur_MIAM_1.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("bureau_chercheur_MIAM_1.png",bitmap);


        bitmap = null;
        bitmap = loadImage("bureau_chercheur_MIAM_2.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("bureau_chercheur_MIAM_2.png",bitmap);


        bitmap = null;
        bitmap = loadImage("bureau_chercheur_MIAM_3.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("bureau_chercheur_MIAM_3.png",bitmap);


        bitmap = null;
        bitmap = loadImage("cafe.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("cafe.png",bitmap);


        bitmap = null;
        bitmap = loadImage("canape.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("canape.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Dupuis.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Dupuis.png",bitmap);


        bitmap = null;
        bitmap = loadImage("E30.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("E30.png",bitmap);


        bitmap = null;
        bitmap = loadImage("E31.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("E31.png",bitmap);


        bitmap = null;
        bitmap = loadImage("E32.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("E32.png",bitmap);


        bitmap = null;
        bitmap = loadImage("E33.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("E33.png",bitmap);


        bitmap = null;
        bitmap = loadImage("E34.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("E34.png",bitmap);

        /*
        bitmap = null;
        bitmap = loadImage("E35.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("E35.png",bitmap);
        */


        bitmap = null;
        bitmap = loadImage("E36.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("E36.png",bitmap);


        bitmap = null;
        bitmap = loadImage("E37.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("E37.png",bitmap);


        bitmap = null;
        bitmap = loadImage("E37-bis.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("E37-bis.png",bitmap);


        bitmap = null;
        bitmap = loadImage("E38.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("E38.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Fondement.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Fondement.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Forestier.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Forestier.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Hassenforder.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Hassenforder.png",bitmap);


        bitmap = null;
        bitmap = loadImage("IARISS.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("IARISS.png",bitmap);


        bitmap = null;
        bitmap = loadImage("labo_lsi.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("labo_lsi.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Lauffenburger.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Lauffenburger.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Laurain.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Laurain.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Ledy.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Ledy.png",bitmap);


        bitmap = null;
        bitmap = loadImage("LSI.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("LSI.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Muller.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Muller.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Orjuela.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Orjuela.png",bitmap);


        bitmap = null;
        bitmap = loadImage("PC_reseaux.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("PC_reseaux.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Perronne.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Perronne.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Pinot.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Pinot.png",bitmap);


        bitmap = null;
        bitmap = loadImage("prof_invite.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("prof_invite.png",bitmap);


        bitmap = null;
        bitmap = loadImage("salle_reunion.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("salle_reunion.png",bitmap);


        bitmap = null;
        bitmap = loadImage("secretariat_miage.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("secretariat_miage.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Studer.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Studer.png",bitmap);


        bitmap = null;
        bitmap = loadImage("tableau_sectoriel.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("tableau_sectoriel.png",bitmap);


        bitmap = null;
        bitmap = loadImage("tableau_sectoriel_ts8.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("tableau_sectoriel_ts8.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Thiry.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Thiry.png",bitmap);


        bitmap = null;
        bitmap = loadImage("toilettes_femmes.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("toilettes_femmes.png",bitmap);


        bitmap = null;
        bitmap = loadImage("toilettes_hommes.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("toilettes_hommes.png",bitmap);


        bitmap = null;
        bitmap = loadImage("toilettes_handicapées.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("toilettes_handicapées.png",bitmap);


        bitmap = null;
        bitmap = loadImage("toilettes_ts7.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("toilettes_ts7.png",bitmap);


        bitmap = null;
        bitmap = loadImage("vestiaire.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("vestiaire.png",bitmap);


        bitmap = null;
        bitmap = loadImage("Weber.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Weber.png",bitmap);bitmap = null;


        bitmap = loadImage("Weisser.png");
        if (bitmap == null){
            return false;
        }
        augmentedImageDatabase.addImage("Weisser.png",bitmap);






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



        //Toast.makeText(this, updateAugmentedimage.size(), Toast.LENGTH_SHORT).show();
        for (AugmentedImage image : updateAugmentedimage){
            if (image.getTrackingState() == TrackingState.TRACKING){



                if (image.getName().equals("Ambs.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.pierreambs);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.pierreambs);

                    }
                    node.setImage(image);
                    arView.getScene().addChild(node);

                    //Intent gameActivity = new Intent(MainActivity.this, Jeu.class);
                    //startActivity(gameActivity);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
                }

                else if (image.getName().equals("canape.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.fauteuil);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.fauteuil);

                    }
                    node.setImage(image);
                    arView.getScene().addChild(node);
                }

                else if (image.getName().equals("cafe.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.tassecafe);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.tassecafe);

                    }
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);

                    if (!hasNode2) {
                        node2 = new MyArNode(this, R.raw.emploidutemps);
                        hasNode2 = true;
                    }
                    else {
                        arView.getScene().removeChild(node2);
                        node2 = new MyArNode();
                        node2.changeModel(this, R.raw.emploidutemps);
                    }
                    node2.translate(image);
                    arView.getScene().addChild(node2);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);

                    if (!hasNode2) {
                        node2 = new MyArNode(this, R.raw.cube38);
                        hasNode2 = true;
                    }
                    else {
                        arView.getScene().removeChild(node2);
                        node2 = new MyArNode();
                        node2.changeModel(this, R.raw.cube38);
                    }
                    node2.translate(image);
                    arView.getScene().addChild(node2);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);

                    if (!hasNode2) {
                        node2 = new MyArNode(this, R.raw.cube38);
                        hasNode2 = true;
                    }
                    else {
                        arView.getScene().removeChild(node2);
                        node2 = new MyArNode();
                        node2.changeModel(this, R.raw.cube38);
                    }
                    node2.translate(image);
                    arView.getScene().addChild(node2);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);

                    if (!hasNode2) {
                        node2 = new MyArNode(this, R.raw.cube38);
                        hasNode2 = true;
                    }
                    else {
                        arView.getScene().removeChild(node2);
                        node2 = new MyArNode();
                        node2.changeModel(this, R.raw.cube38);
                    }
                    node2.translate(image);
                    arView.getScene().addChild(node2);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);

                    if (!hasNode2) {
                        node2 = new MyArNode(this, R.raw.cube38);
                        hasNode2 = true;
                    }
                    else {
                        arView.getScene().removeChild(node2);
                        node2 = new MyArNode();
                        node2.changeModel(this, R.raw.cube38);
                    }
                    node2.translate(image);
                    arView.getScene().addChild(node2);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);

                    if (!hasNode2) {
                        node2 = new MyArNode(this, R.raw.cube38);
                        hasNode2 = true;
                    }
                    else {
                        arView.getScene().removeChild(node2);
                        node2 = new MyArNode();
                        node2.changeModel(this, R.raw.cube38);
                    }
                    node2.translate(image);
                    arView.getScene().addChild(node2);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);

                    if (!hasNode2) {
                        node2 = new MyArNode(this, R.raw.cube38);
                        hasNode2 = true;
                    }
                    else {
                        arView.getScene().removeChild(node2);
                        node2 = new MyArNode();
                        node2.changeModel(this, R.raw.cube38);
                    }
                    node2.translate(image);
                    arView.getScene().addChild(node2);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);

                    if (!hasNode2) {
                        node2 = new MyArNode(this, R.raw.cube38);
                        hasNode2 = true;
                    }
                    else {
                        arView.getScene().removeChild(node2);
                        node2 = new MyArNode();
                        node2.changeModel(this, R.raw.cube38);
                    }
                    node2.translate(image);
                    arView.getScene().addChild(node2);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);

                    if (!hasNode2) {
                        node2 = new MyArNode(this, R.raw.cube38);
                        hasNode2 = true;
                    }
                    else {
                        arView.getScene().removeChild(node2);
                        node2 = new MyArNode();
                        node2.changeModel(this, R.raw.cube38);
                    }
                    node2.translate(image);
                    arView.getScene().addChild(node2);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
                }

                else if (image.getName().equals("Laurain.png")){
                    if (!hasNode){
                        node = new MyArNode(this, R.raw.thomaslaurain);
                        hasNode = true;
                    }
                    else{
                        arView.getScene().removeChild(node);
                        node = new MyArNode();
                        node.changeModel(this, R.raw.thomaslaurain);

                    }
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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
                    node.setImage(image);
                    arView.getScene().addChild(node);
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

    private void zoomImageFromThumb(final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
        expandedImageView.setImageResource(imageResId);
        View plan = findViewById(R.id.planLayout);
        plan.setVisibility(View.VISIBLE);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(shortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                plan.setVisibility(View.INVISIBLE);
                set.start();
                currentAnimator = set;

            }
        });
    }



}