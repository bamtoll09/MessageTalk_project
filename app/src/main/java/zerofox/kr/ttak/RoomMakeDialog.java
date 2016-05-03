package zerofox.kr.ttak;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 재현 on 2015-11-30.
 */
public class RoomMakeDialog extends DialogFragment {

    private int PICK_IMAGE_REQUEST = 1;

    CircleImageView mIcon;
    EditText mTitle, mText;

    static int REQUIRED_SIZE;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        final View roomMakeView = inflater.inflate(R.layout.dia_room_make, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(roomMakeView)
                // Add action buttons
                .setPositiveButton(R.string.add_room, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        String title = mTitle.getText().toString(),
                                text = mText.getText().toString();
                        if (title.equals(""))
                            title = "제목";
                        if (text.equals(""))
                            text = "글";
                        MainActivity.listViewAdapter.add(mIcon.getDrawable(), title, text, new SimpleDateFormat("a hh:mm").format(new Date(System.currentTimeMillis())));
                        MainActivity.listViewAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RoomMakeDialog.this.getDialog().cancel();
                    }
                });

        mIcon = (CircleImageView) roomMakeView.findViewById(R.id.room_icon);
        mTitle = (EditText) roomMakeView.findViewById(R.id.room_title);
        mText = (EditText) roomMakeView.findViewById(R.id.room_member);

        mIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //Show only images, no videos or anything else
                intent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                //Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                return false;
            }
        });
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri uri = data.getData();

            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            CursorLoader cursorLoader = new CursorLoader(getActivity(), selectedImageUri, projection, null, null,
                    null);
            Cursor cursor = cursorLoader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();

            String selectedImagePath = cursor.getString(column_index);

            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);

            int density = getResources().getDisplayMetrics().densityDpi;
            int scale = 1;

            switch (density) {
                case DisplayMetrics.DENSITY_LOW:
                    REQUIRED_SIZE = 54;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    REQUIRED_SIZE = 72;
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    REQUIRED_SIZE = 108;
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    REQUIRED_SIZE = 144;
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    REQUIRED_SIZE = 216;
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    REQUIRED_SIZE = 288;
                    break;
            }

            mIcon.getLayoutParams().height = REQUIRED_SIZE;

            while (options.outHeight / scale - 10 >= REQUIRED_SIZE) {
                scale += 10;
            }

            options.inSampleSize = scale; // 1 이하의 값은 무조건 1로 세팅, 1/scale 만큼 크기가 작아진다.
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(selectedImagePath, options);

            mIcon.setImageBitmap(bm);//Drawable(getResources().getDrawable(R.drawable.ic_default));
        }
    }
}
