import ui.activity.Activity;
import ui.view.Button;
import ui.view.LayoutInflater;

public class TileSetEditorActivity extends Activity {

    @Override
    public void onCreate () {
        LayoutInflater layoutInflater = new LayoutInflater();
        this.view = layoutInflater.inflate("activity_tileset_editor.xml", null);
    }
}
