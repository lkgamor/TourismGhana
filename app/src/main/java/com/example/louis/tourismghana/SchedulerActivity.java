package com.example.louis.tourismghana;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class SchedulerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SchedulerDBHelper schedulerDBHelper;
    private FloatingActionButton addNewPlan;
    private TextView subTitle;
    private ImageView imageView;
    private ArrayAdapter<String> arrayAdapter;
    private ListView schedulerlistView;

    private String time, date, plan, getTime, getDate, getPlan;
    public static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        schedulerDBHelper = new SchedulerDBHelper(this);

        schedulerlistView = (ListView) findViewById(R.id.tourScehduleListView);
        imageView = (ImageView) findViewById(R.id.plan_icon);
        addNewPlan = (FloatingActionButton) findViewById(R.id.addPlanBtn);
        subTitle = (TextView) findViewById(R.id.title_sub);

        showPlans();


        addNewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SchedulerActivity.this);
                builder.setTitle("New Schedule");
                LayoutInflater inflater = getLayoutInflater();
                View getPlanView = inflater.inflate(R.layout.add_plan_layout, null);
                builder.setView(getPlanView);
                final EditText planText = (EditText) getPlanView.findViewById(R.id.getPlan);
                builder.setNegativeButton("CANCEL", null);
                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        plan = planText.getText().toString();

                        if (plan.isEmpty()) {
                            Snackbar.make(view, "Provide a schedule content!", Snackbar.LENGTH_LONG).show();
                        } else {

                            Calendar  calendar = Calendar.getInstance();
                            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

                            int yy = calendar.get(Calendar.YEAR);
                            int mm = calendar.get(Calendar.MONTH);
                            int dd = calendar.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(SchedulerActivity.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    date = dayOfMonth + "-" + month + "-" + year;

                                    schedulerDBHelper.addPlan(plan, date);
                                    Snackbar.make(view, "New Plan Successfully Scheduled!", Snackbar.LENGTH_SHORT).show();
                                    showPlans();
                                }
                            }, yy,mm,dd);

                            datePickerDialog.show();
                        }
                    }
                });
                builder.show();
            }
        });

        schedulerlistView.setOnItemClickListener(this);
    }

    private void showPlans() {

        ArrayList<String> planList = schedulerDBHelper.getPlanList();
        if (arrayAdapter == null) {
            arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_schedule_layout, R.id.tour_plan, planList);
            schedulerlistView.setAdapter(arrayAdapter);

            count = arrayAdapter.getCount();
            if (count == 0) {
                subTitle.setText("You have no tour plans at the moment");
                imageView.setVisibility(View.VISIBLE);
            } else if (count == 1) {
                subTitle.setText("You have " + count + " tour plan");
                imageView.setVisibility(View.GONE);
            } else {
                subTitle.setText("You have " + count + " tour plans");
                imageView.setVisibility(View.GONE);
            }

        } else {
            arrayAdapter.clear();
            arrayAdapter.addAll(planList);
            arrayAdapter.notifyDataSetChanged();

            count = arrayAdapter.getCount();
            if (count == 0) {
                subTitle.setText("You have no tour plans at the moment");
            } else if (count == 1) {
                subTitle.setText("You have " + count + " tour plan");
            } else {
                subTitle.setText("You have " + count + " tour plans");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setMessage("This plan will be deleted");
        builder.setNegativeButton("CANCEL", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                TextView textView = (TextView) findViewById(R.id.tour_plan);
                String item = String.valueOf(textView.getText());
                schedulerDBHelper.deletePlan(item);
                showPlans();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
