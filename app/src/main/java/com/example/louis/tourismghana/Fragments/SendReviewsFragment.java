package com.example.louis.tourismghana.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.tourismghana.FeedBackResponse;
import com.example.louis.tourismghana.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendReviewsFragment extends Fragment {

    EditText name, email, message;
    String Name, Email, Message, Date;
    TextView sendText, toastText;
    View mainView;
    ImageView toastImage;
    LinearLayout toastLayout;
    Toast toast;
    private Calendar calander;
    private SimpleDateFormat simpleDateFormat;
    private ProgressDialog dialog;
    private static final String DATE_TIME = "dateTime";
    private static final String USER_NAME_KEY = "name";
    private static final String USER_EMAIL_KEY = "email";
    private static final String USER_MESSAGE_KEY = "message";
    private FirebaseFirestore firestore;
    
    public SendReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_reviews, container, false);

        getActivity().setTitle("Your Reviews");
        //Getting a reference to the Menu Bar's items
        setHasOptionsMenu(true);

        //Initializing our FIREBASE FIRESTORE Object
        firestore = FirebaseFirestore.getInstance();
        toastLayout = new LinearLayout(getActivity());
        toast = new Toast(getActivity());
        toastText = new TextView(getActivity());
        toastImage = new ImageView(getActivity());

        name = (EditText) view.findViewById(R.id.name);
        email = (EditText) view.findViewById(R.id.email);
        message = (EditText) view.findViewById(R.id.message);
        CardView sendFeedBack = (CardView) view.findViewById(R.id.sendFeedBack);
        mainView = view.findViewById(R.id.mainView);
        sendText = (TextView) view.findViewById(R.id.mySendText);
        sendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SEND_FEEDBACK();

            }
        });
    
        return view;
    }

    public void SEND_FEEDBACK() {

        Name = name.getText().toString();
        Email = email.getText().toString();
        Message = message.getText().toString();

        if (Name.isEmpty()) {
            name.setError("Please add your name");
        } else if (Email.isEmpty()) {
            email.setError("Your email");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Invalid Email Address Format");
        } else if (Message.isEmpty()) {
            message.setError("No message entered");
        } else {
            startDialog();

            calander = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            Date = simpleDateFormat.format(calander.getTime());

            Map<String, Object> userFeedBack = new HashMap<String, Object>();
            userFeedBack.put(USER_NAME_KEY, Name);
            userFeedBack.put(USER_EMAIL_KEY, Email);
            userFeedBack.put(USER_MESSAGE_KEY, Message);
            userFeedBack.put(DATE_TIME, Date);

            firestore.collection("User_Responses").add(userFeedBack).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    //Clearing the EditText fields
                    name.setText("");
                    email.setText("");
                    message.setText("");

                    //Stopping the progressbar
                    stopDialog();

                    //Displaying the Success message
                    successToast();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Stopping the progressbar
                    stopDialog();

                    //Changing the Name of the button
                    sendText.setText("Resend");

                    //Displaying the Failure Message
                    failureToast();
                }
            });
        }
    }

    private void successToast() {

        Intent intent = new Intent(getActivity(), FeedBackResponse.class);
        intent.putExtra("SUCCESS", true);
        startActivity(intent);

    }

    private void failureToast() {

        Intent intent = new Intent(getActivity(), FeedBackResponse.class);
        intent.putExtra("SUCCESS", false);
        startActivity(intent);
    }

    public void startDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Sending FeedBack....");
        dialog.setCancelable(false);
        dialog.show();
    }

    public void stopDialog() {
        dialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                getActivity().finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

