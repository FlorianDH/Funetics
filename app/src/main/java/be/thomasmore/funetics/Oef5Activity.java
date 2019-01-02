package be.thomasmore.funetics;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Oef5Activity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener{

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    private int score = 0;
    private int aantalPogingen = 0;

    private FloatingActionButton nextFAB;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    private static final String IMAGE_VIEW1_TAG = "Image1";
    private static final String IMAGE_VIEW2_TAG = "Image2";
    private static final String IMAGE_VIEW3_TAG = "Image3";
    private static final String IMAGE_VIEW4_TAG = "Image4";

    private boolean isPlaying = false; //false by default
    private MediaPlayer audioPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef5);

        Bundle bundle = getIntent().getExtras();
        Long doelwoordId = bundle.getLong("doelwoordId");
        Long kindId = bundle.getLong("kindId");

        db = new DatabaseHelper(this);

        huidigDoelwoord = db.getDoelwoordById(doelwoordId);
        huidigKind = db.getKindById(kindId);

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        TextView textWoord = (TextView) findViewById(R.id.textViewWoord);
        textWoord.setText(huidigDoelwoord.getNaam());

        nextFAB = (FloatingActionButton) findViewById(R.id.nextFAB);
        nextFAB.setVisibility(View.INVISIBLE); //Maak de volgende-knop onzichtbaar tot er 3 woorden zijn aangeduid

        FloatingActionButton soundFab = (FloatingActionButton) findViewById(R.id.soundFAB);
        soundFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButton_onClick();
            }
        });

        findViews();
        implementEvents();
    }

    public void nextFAB_onClick(View view) {
        if (true){
            score++;
            aantalPogingen++;

            //Terug naar oefening activity
            Intent returnIntent = new Intent();
            returnIntent.putExtra("score", String.valueOf(score));
            returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
        else {
            aantalPogingen++;
        }
    }

    public void playButton_onClick() {
        if (isPlaying){
            audioPlayer.stop();
        }

        isPlaying = true;
        playAudioPlayer();
    }

    public void playAudioPlayer(){
        isPlaying = true;
        audioPlayer = MediaPlayer.create(getApplicationContext(), R.raw.duikbril);
        audioPlayer.start();
    }

    //Find all views and set Tag to all draggable views
    private void findViews() {
        imageView1 = (ImageView) findViewById(R.id.imageView);
        imageView1.setTag(IMAGE_VIEW1_TAG);
        imageView1.setImageResource(R.drawable.duikbril);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setTag(IMAGE_VIEW2_TAG);
        imageView2.setImageResource(R.drawable.zaklamp);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView3.setTag(IMAGE_VIEW3_TAG);
        imageView3.setImageResource(R.drawable.klimtouw);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView4.setTag(IMAGE_VIEW4_TAG);
        imageView4.setImageResource(R.drawable.riet);
    }


    //Implement long click and drag listener
    private void implementEvents() {
        //add or remove any view that you don't want to be dragged
        imageView1.setOnLongClickListener(this);
        imageView2.setOnLongClickListener(this);
        imageView3.setOnLongClickListener(this);
        imageView4.setOnLongClickListener(this);

        //add or remove any layout view that you don't want to accept dragged view
        findViewById(R.id.top_layout).setOnDragListener(this);
        findViewById(R.id.fout_layout).setOnDragListener(this);
        findViewById(R.id.juist_layout).setOnDragListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        // Create a new ClipData.
        // This is done in two steps to provide clarity. The convenience method
        // ClipData.newPlainText() can create a plain text ClipData in one step.

        // Create a new ClipData.Item from the ImageView object's tag
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

        // Instantiates the drag shadow builder.
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Starts the drag
        view.startDrag(data//data to be dragged
                , shadowBuilder //drag shadow
                , view//local data about the drag and drop operation
                , 0//no needed flags
        );

        //Set view visibility to INVISIBLE as we are going to drag the view
        view.setVisibility(View.INVISIBLE);
        return true;
    }

    // This is the method that the system calls when it dispatches a drag event to the
    // listener.
    @Override
    public boolean onDrag(View view, DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();
        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept
                    // data.

                    //view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);//set background color to your view

                    // Invalidate the view to force a redraw in the new tint
                    //view.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                // Return true; the return value is ignored.

                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.

                // view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                // String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
                //  Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();

                // Turns off any color tints
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                owner.removeView(v);//remove the dragged view
                LinearLayout container = (LinearLayout) view;//caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                container.addView(v);//Add the dragged view
                v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE

                // Returns true. DragEvent.getResult() will return true.
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                // Does a getResult(), and displays what happened.
//                if (event.getResult())
//                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
//
//                else
//                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
//

                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }



}
