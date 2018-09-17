package views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.gasalabert.falldown.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Constants;
import model.UserAccount;

/**
 * Created by Gabin on 23/03/2018.
 */

import static model.PlatformManager.score;

public class GameOver extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference(UserAccount.getInstance().getAccount().getIdToken());
        //myRef.setValue(score);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View layout = inflater.inflate(R.layout.game_over, null);
        builder.setView(layout)
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Constants.CURRENT_GPS.reset();
                    }
                })
                .setNegativeButton(R.string.back_menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        getDialog().setCanceledOnTouchOutside(false);
        super.onResume();
    }
}
