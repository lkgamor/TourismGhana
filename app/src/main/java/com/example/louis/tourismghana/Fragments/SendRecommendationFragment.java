package com.example.louis.tourismghana.Fragments;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.tourismghana.Adapters.Site;
import com.example.louis.tourismghana.Adapters.Suggestions;
import com.example.louis.tourismghana.FeedBackResponse;
import com.example.louis.tourismghana.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendRecommendationFragment extends Fragment {

    EditText name, email, site_name, message;
    String Name, Email, Site_Name, Location, Message, Date;
    TextView sendText, toastText, site_location, addSiteText;
    ImageView siteImage, alt_SiteImage;
    View mainView;
    LinearLayout toastLayout;
    Toast toast;
    private Uri imageUri;
    private Calendar calander;
    private SimpleDateFormat simpleDateFormat;
    private ProgressDialog dialog;
    private CardView location, addImage;
    private static final String DATE_TIME = "dateTime";
    private static final String USER_NAME_KEY = "name";
    private static final String USER_EMAIL_KEY = "email";
    private static final String SITE_NAME_KEY = "message";
    private static final String LOCATION_KEY = "message";
    private static final String USER_MESSAGE_KEY = "message";
    private FirebaseFirestore firestore;
    private StorageReference storageReference;

    private static int PLACE_PICKER_REQUEST_LOCATION = 1;
    private static int CAMERA_CODE = 2;
    private StorageTask task;

    public SendRecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_recommendation, container, false);

        getActivity().setTitle("Recommend A New Site");

        //Initializing our FIREBASE FIRESTORE Object
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("Suggested_Site_Images");
        toastLayout = new LinearLayout(getActivity());
        toast = new Toast(getActivity());
        toastText = new TextView(getActivity());

        name = (EditText) view.findViewById(R.id.name);
        email = (EditText) view.findViewById(R.id.email);
        site_name = (EditText) view.findViewById(R.id.siteName);
        location = (CardView) view.findViewById(R.id.location);
        addImage = (CardView) view.findViewById(R.id.addImage);
        message = (EditText) view.findViewById(R.id.message);
        CardView sendFeedBack = (CardView) view.findViewById(R.id.sendFeedBack);
        mainView = view.findViewById(R.id.mainView);
        siteImage = (ImageView) view.findViewById(R.id.siteImage);
        alt_SiteImage = (ImageView) view.findViewById(R.id.addImageIcon);
        sendText = (TextView) view.findViewById(R.id.mySendText);
        addSiteText = (TextView) view.findViewById(R.id.addImageText);
        site_location = (TextView) view.findViewById(R.id.site_location);

        sendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (task != null && task.isInProgress())
                {
                    Toast.makeText(getActivity(), "Upload already in progress", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SEND_FEEDBACK();
                }

            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder placePickerbuilder = new PlacePicker.IntentBuilder();
                Intent placeIntent;

                try {

                    placeIntent = placePickerbuilder.build(getActivity());
                    startActivityForResult(placeIntent, PLACE_PICKER_REQUEST_LOCATION);

                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {

                    e.printStackTrace();

                }
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CAMERA_CODE);
            }
        });
        return view;
    }

    //setting DATA COLLECTED BY PLACEPICKER
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //GETTING THE NAME OF THE SELECTED LOCATION
        if (requestCode == PLACE_PICKER_REQUEST_LOCATION && resultCode == RESULT_OK) {

            Place place = PlacePicker.getPlace(getActivity(), data);
            Location = String.format("%s", place.getName());
            site_location.setText(Location);

        }

        //GETTING THE IMAGE OF SUGGESTED SITE
        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK)
        {
            imageUri = data.getData();
            alt_SiteImage.setVisibility(View.GONE);
            addSiteText.setVisibility(View.GONE);
            siteImage.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(imageUri).fit().centerCrop().into(siteImage);
        }
    }

    public void SEND_FEEDBACK() {

        Name = name.getText().toString();
        Email = email.getText().toString();
        Site_Name = site_name.getText().toString();
        Message = message.getText().toString();

        if (Name.isEmpty()) {
            name.setError("Please add your name");
        } else if (Email.isEmpty()) {
            email.setError("Your email");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Invalid Email Address Format");
        } else if (Site_Name.isEmpty()) {
            message.setError("Provide the name of the new site");
        } else if (Location.isEmpty()) {
            message.setError("Provide location data");
        } else if (Message.isEmpty()) {
            message.setError("Brief description required");
        }else if (imageUri == null) {
            Toast.makeText(getActivity(), "No Image Added", Toast.LENGTH_SHORT).show();
        } else {

            startDialog();

            calander = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            Date = simpleDateFormat.format(calander.getTime());

            Map<String, Object> userFeedBack = new HashMap<String, Object>();
            userFeedBack.put(USER_NAME_KEY, Name);
            userFeedBack.put(USER_EMAIL_KEY, Email);
            userFeedBack.put(SITE_NAME_KEY, Site_Name);
            userFeedBack.put(LOCATION_KEY, Location);
            userFeedBack.put(USER_MESSAGE_KEY, Message);
            userFeedBack.put(DATE_TIME, Date);

            //SAVING THE SUGGESTED SITE IMAGE TO FIRE_BASE STORAGE
            StorageReference storage = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            task = storage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Suggestions siteSuggestion = new Suggestions(Name, Email, Site_Name, Location, Message, taskSnapshot.getDownloadUrl().toString());
                    //String suggestedSiteUpload = firestore.collection("Site_Suggestions").add()
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            firestore.collection("Site_Suggestions").add(userFeedBack).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    //Clearing the EditText fields
                    name.setText("");
                    email.setText("");
                    site_name.setText("");
                    site_location.setText("ADD SITE LOCATION");
                    message.setText("");
                    siteImage.setVisibility(View.GONE);
                    alt_SiteImage.setVisibility(View.VISIBLE);
                    addSiteText.setVisibility(View.VISIBLE);
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


    //Getting the file extension type of the chosen image
    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
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
        dialog.setMessage("Sending Suggestion....");
        dialog.setCancelable(false);
        dialog.show();
    }

    public void stopDialog() {
        dialog.dismiss();
    }

}
