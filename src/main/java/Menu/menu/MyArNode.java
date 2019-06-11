package Menu.menu;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.concurrent.CompletableFuture;

public class MyArNode extends AnchorNode {
    private AugmentedImage image;
    private static CompletableFuture<ModelRenderable> modelRenderableCompletableFuture;


    public MyArNode(Context context, int modelID){

        if (modelRenderableCompletableFuture == null){
            modelRenderableCompletableFuture = ModelRenderable.builder()
                    .setRegistryId("my_model")
                    .setSource(context,modelID)
                    .build();
        }
    }

    public MyArNode(){
        super();
    }

    public void changeModel(Context c, int model){
        this.modelRenderableCompletableFuture = ModelRenderable.builder()
                .setRegistryId("my_model")
                .setSource(c,model)
                .build();
    }

    public void setImage(AugmentedImage image) {
        this.image = image;
        if (!modelRenderableCompletableFuture.isDone()){
            CompletableFuture.allOf(modelRenderableCompletableFuture)
                    .thenAccept((Void aVoid) ->{
                        setImage(image);
                    }).exceptionally(throwable -> {
                return null;
            });
        }
        setAnchor(image.createAnchor(image.getCenterPose()));
        Node node = new Node();
        Pose pose = Pose.makeTranslation(0.0f,0.0f,0.0f);
        node.setParent(this);
        node.setLocalPosition(new Vector3(pose.tx(),pose.ty(), pose.tz()));
        node.setLocalRotation(new Quaternion(pose.qx(),pose.qy()-90,pose.qz(),pose.qw()));
        //node.setLocalRotation(new Quaternion(pose.qx(),pose.qy(),pose.qz(),pose.qw()));


        node.setLocalScale(new Vector3(5f,5f,5f));




        node.setRenderable(modelRenderableCompletableFuture.getNow(null));


    }

    public AugmentedImage getImage() {
        return image;
    }
}
