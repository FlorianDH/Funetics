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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.Arrays;
import java.util.Collections;

public class Oef5Activity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener, MediaPlayer.OnCompletionListener{

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

    private LinearLayout juistLayout;
    private LinearLayout foutLayout;
    private LinearLayout topLayout;

    private static final String IMAGE_VIEW1_TAG = "Image1";
    private static final String IMAGE_VIEW2_TAG = "Image2";
    private static final String IMAGE_VIEW3_TAG = "Image3";
    private static final String IMAGE_VIEW4_TAG = "Image4";

    private MediaPlayer audioPlayer = null;
    private MediaPlayer juistPlayer = null;
    private MediaPlayer foutPlayer = null;

    private int[] tracks = new int[2];
    private int currentTrack = 0;

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

        setAudioPlayer();

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        TextView textWoord = (TextView) findViewById(R.id.textViewWoord);
        textWoord.setText(huidigDoelwoord.getNaam());

        juistLayout = (LinearLayout) findViewById(R.id.juist_layout);
        foutLayout = (LinearLayout) findViewById(R.id.fout_layout);
        topLayout = (LinearLayout) findViewById(R.id.top_layout);

        nextFAB = (FloatingActionButton) findViewById(R.id.nextFAB);
        nextFAB.setVisibility(View.VISIBLE); //Maak de volgende-knop onzichtbaar tot er 3 woorden zijn aangeduid

        FloatingActionButton soundFab = (FloatingActionButton) findViewById(R.id.soundFAB);
        soundFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButton_onClick();
            }
        });

        findViews();
        setImages();
        implementEvents();
        playAudioPlayer();
    }

    public void nextFAB_onClick(View view) {
        stopPlaying();

        setAudioPlayer();

        juistLayout = (LinearLayout) findViewById(R.id.juist_layout);
        foutLayout = (LinearLayout) findViewById(R.id.fout_layout);

        int juisteAfbeeldingen = 0;

        for(int i=0;i<juistLayout.getChildCount();i++)
        {
            ImageView image =  (ImageView) juistLayout.getChildAt(i);
            if (image.getContentDescription().equals("juist")){
                juisteAfbeeldingen ++;
            }
        }

        for(int i=0;i<foutLayout.getChildCount();i++)
        {
            ImageView image =  (ImageView) foutLayout.getChildAt(i);
            if (image.getContentDescription().equals("fout")){
                juisteAfbeeldingen ++;
            }
        }

        aantalPogingen++;

        if(juisteAfbeeldingen == 4){
            score ++;

            juistPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    while (mediaPlayer.isPlaying()){}
                }
            });

            //Terug naar oefening activity
            Intent returnIntent = new Intent();
            returnIntent.putExtra("score", String.valueOf(score));
            returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }else{
            foutPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        }
    }

    public void playButton_onClick() {
        stopPlaying();

        currentTrack = 0;
        playAudioPlayer();
    }

    private void stopPlaying() {
        if (audioPlayer != null) {
            try{
                audioPlayer.reset();
                audioPlayer.prepare();
                audioPlayer.stop();
                audioPlayer.release();
                audioPlayer = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        if (foutPlayer != null) {
            try{
                foutPlayer.reset();
                foutPlayer.prepare();
                foutPlayer.stop();
                foutPlayer.release();
                foutPlayer = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        if (juistPlayer != null) {
            try{
                juistPlayer.reset();
                juistPlayer.prepare();
                juistPlayer.stop();
                juistPlayer.release();
                juistPlayer = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public void setAudioPlayer() {
        tracks[0] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_5", "raw", getPackageName()); //oefenwoord_herhaal
        tracks[1] = R.raw.oef5_1;

        juistPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef5_2);
        foutPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef5_3);
    }

    public void playAudioPlayer(){
        if(this.audioPlayer != null){
            this.audioPlayer.release();
        }
        audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
        audioPlayer.setOnCompletionListener(this);
        audioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    public void onCompletion(MediaPlayer audioPlayer2) {
        if (currentTrack < tracks.length-1) {
            currentTrack++;
            audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
            audioPlayer.setOnCompletionListener(this);
            audioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        }else {
            stopPlaying();
        }
    }

    //Find all views and set Tag to all draggable views
    private void findViews() {
        imageView1 = (ImageView) findViewById(R.id.imageView1);
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

    private void setImages(){
        String shuffleArray[][] = new String [][] {
                { "_5_1", "juist"},
                { "_5_2", "juist"},
                { "_5_3", "juist"},
                { "_5_4", "fout"}};
        Collections.shuffle(Arrays.asList(shuffleArray));

        imageView1.setImageResource(getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + shuffleArray[0][0], "drawable", getPackageName()));
        imageView1.setContentDescription(shuffleArray[0][1]);
        imageView2.setImageResource(getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + shuffleArray[1][0], "drawable", getPackageName()));
        imageView2.setContentDescription(shuffleArray[1][1]);
        imageView3.setImageResource(getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + shuffleArray[2][0], "drawable", getPackageName()));
        imageView3.setContentDescription(shuffleArray[2][1]);
        imageView4.setImageResource(getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + shuffleArray[3][0], "drawable", getPackageName()));
        imageView4.setContentDescription(shuffleArray[3][1]);


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
//
//                 Does a getResult(), and displays what happened.
                if (event.getResult()){
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
                    View vImage = (View) event.getLocalState();
                    vImage.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                }


                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }

//    public void buttonRestart_onClick(View view){
//        stopPlaying();
//        findViews();
//
//        ViewGroup owner1 = (ViewGroup) imageView1.getParent();
//        owner1.removeView(imageView1);
//        topLayout.addView(imageView1);
//        imageView1.setVisibility(View.VISIBLE);
//
//        ViewGroup owner2 = (ViewGroup) imageView2.getParent();
//        owner2.removeView(imageView2);
//        topLayout.addView(imageView2);
//        imageView2.setVisibility(View.VISIBLE);
//
//
//        ViewGroup owner3 = (ViewGroup) imageView3.getParent();
//        owner3.removeView(imageView3);
//        topLayout.addView(imageView3);
//        imageView3.setVisibility(View.VISIBLE);
//
//
//        ViewGroup owner4 = (ViewGroup) imageView4.getParent();
//        owner4.removeView(imageView4);
//        topLayout.addView(imageView4);
//        imageView4.setVisibility(View.VISIBLE);
//
//        setImages();
//    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopPlaying();
    }

}
