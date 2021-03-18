import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.shape.Sphere;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;



/**
 * Sample 5 - how to map keys and mousebuttons to actions
 */
public class StarTrek extends SimpleApplication {

    public static void main(String[] args) {
        StarTrek app = new StarTrek();
        app.start();
    }

    protected Geometry sphere;
    protected Geometry cube;
    protected Geometry cube2;
    private boolean running = true;


    @Override
    public void simpleInitApp() {
        
        
        Sphere planet = new Sphere(30, 30, 30, false, true);
        sphere = new Geometry("Sphere", planet);
        sphere.setLocalTranslation(new Vector3f(0, 0, 0));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap",
            assetManager.loadTexture("Common/MatDefs/Water/Textures/foam3.jpg"));
        mat.setColor("Color", ColorRGBA.Blue);
        sphere.setMaterial(mat);
        
        Box borgCube = new Box(8,8,8);
        cube = new Geometry("Box", borgCube);
        cube.setLocalTranslation(new Vector3f(20, 10, 25));
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap",
            assetManager.loadTexture("MatDefs/borgCube.jpg"));
        mat1.setColor("Color", ColorRGBA.Gray);
        cube.setMaterial(mat1);
       
        Box borgCube2 = new Box(8,8,8);
        cube2 = new Geometry("Box", borgCube2);
        cube2.setLocalTranslation(new Vector3f(-20, 10, 25));
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setTexture("ColorMap",
            assetManager.loadTexture("MatDefs/borgCube.jpg"));
        mat2.setColor("Color", ColorRGBA.Gray);
        cube2.setMaterial(mat2);
        
        stateManager.getState(FlyCamAppState.class).setEnabled(false); //Turn off the Fly Cam Controller
        
       /**
        * Make the Background
        */

        Material backgroundMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture backgroundTex = assetManager.loadTexture("Interface/space.png");
        backgroundMat.setTexture("ColorMap", backgroundTex);
        float w = this.getContext().getSettings().getWidth();
        float h = this.getContext().getSettings().getHeight();
        float ratio = (w/h)*100;
        cam.setLocation(Vector3f.ZERO.add(new Vector3f(0.0f, 0.0f,100f)));
        float camZ = cam.getLocation().getZ()-15; 
        float width = ratio;
        float height = camZ;
        Quad fsq = new Quad(width, height);
        Geometry backgroundGeom = new Geometry("Background", fsq);
        backgroundGeom.setQueueBucket(Bucket.Sky);
        backgroundGeom.setCullHint(CullHint.Never);
        backgroundGeom.setMaterial(backgroundMat);
        backgroundGeom.setLocalTranslation(-(width / 2), -(height/ 2), 0);

        initKeys(); // load custom keybinding
       
        
        rootNode.attachChild(backgroundGeom);
        rootNode.attachChild(cube);
        rootNode.attachChild(cube2);
        rootNode.attachChild(sphere);
    }

    /**
     * Custom Keybinding: Map named actions to inputs.
     */
    private void initKeys() {
        // You can map one or several inputs to one named action
        inputManager.addMapping("Up",   new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("Down",  new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Left",  new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("Up2",   new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down2",  new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Right2",  new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Left2",  new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(analogListener, "Up", "Down", "Right", "Left","Up2", "Down2", "Right2", "Left2");
       
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        // make the planet rotate:
        sphere.rotate(0, 1*tpf, 0);
    }
    
    
    
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (running) {
                if (name.equals("Right")) {
                    Vector3f v1 = cube.getLocalTranslation();
                    cube.setLocalTranslation(v1.x - value * 30, v1.y, v1.z);
                    
                }
                if (name.equals("Left")) {
                    Vector3f v1 = cube.getLocalTranslation();
                    cube.setLocalTranslation(v1.x + value * 30, v1.y, v1.z);
                }
                if (name.equals("Up")) {
                    Vector3f v1 = cube.getLocalTranslation();
                    cube.setLocalTranslation(v1.x, v1.y + value * 30, v1.z);
                }
                if (name.equals("Down")) {
                    Vector3f v1 = cube.getLocalTranslation();
                    cube.setLocalTranslation(v1.x, v1.y - value * 30, v1.z);
                } 
                //Controls the second cube
                if (name.equals("Right2")) {
                    Vector3f v2 = cube2.getLocalTranslation();
                    cube2.setLocalTranslation(v2.x - value * 30, v2.y, v2.z);
                    
                }
                if (name.equals("Left2")) {
                    Vector3f v2 = cube2.getLocalTranslation();
                    cube2.setLocalTranslation(v2.x + value * 30, v2.y, v2.z);
                }
                if (name.equals("Up2")) {
                    Vector3f v2 = cube2.getLocalTranslation();
                    cube2.setLocalTranslation(v2.x, v2.y + value * 30, v2.z);
                }
                if (name.equals("Down2")) {
                    Vector3f v2 = cube2.getLocalTranslation();
                    cube2.setLocalTranslation(v2.x, v2.y - value * 30, v2.z);
                }
                
              }
            } 
        };
    }
    

    