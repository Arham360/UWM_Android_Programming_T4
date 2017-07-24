package edu.uwm.android.diabetes.Activities;

        import android.app.DatePickerDialog;
        import android.database.Cursor;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.Toast;

        import java.util.Calendar;

        import edu.uwm.android.diabetes.Database.DatabaseHandler;
        import edu.uwm.android.diabetes.Database.Regimen;
        import edu.uwm.android.diabetes.R;

public class RegimenActivity extends AppCompatActivity {

    ImageButton homeButton;
    Button addRegimen, showRegimen;
    DatabaseHandler databaseHandler;
    EditText foodDescription, regimenDate;
    Calendar calendar;
    int day, month, year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regimen);
        databaseHandler = new DatabaseHandler(this);
        foodDescription = (EditText) findViewById(R.id.editText_foodDescription);
        addRegimen = (Button) findViewById(R.id.addRegimen);
        showRegimen = (Button) findViewById(R.id.showData);
        homeButton = (ImageButton) findViewById(R.id.regimenHomeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addRegimen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("The add Regimen button is called here.");
                Regimen regimen = new Regimen();
                regimen.setDescription(foodDescription.getText().toString());
                databaseHandler.add(regimen);
                Toast.makeText(RegimenActivity.this, foodDescription.getText().toString() + " Added!",
                        Toast.LENGTH_LONG).show();

            }
        });
        showRegimen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regimen regimen = new Regimen();
                Cursor cursor = databaseHandler.getData(regimen);
                if (cursor.getCount() == 0) {
                    Toast.makeText(RegimenActivity.this, "No data to show", Toast.LENGTH_LONG).show();
                } else {
                    StringBuffer stringBuffer = new StringBuffer();
                    while (cursor.moveToNext()) {
                        stringBuffer.append("ID " + cursor.getString(0) + "\n");
                        stringBuffer.append("Description  " + cursor.getString(1) + "\n");
                    }
                    Toast.makeText(RegimenActivity.this, stringBuffer.toString(), Toast.LENGTH_LONG).show();

                }
            }
        });

        regimenDate = (EditText) findViewById(R.id.regimenDate);
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        regimenDate.setText(month+1  + "/" + day+ "/" + "/" + year);

        regimenDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog();
            }
        });

    }


    public void DateDialog() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                regimenDate.setText(monthOfYear + "/" + dayOfMonth + "/" + year);

            }
        };

        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();

    }
}