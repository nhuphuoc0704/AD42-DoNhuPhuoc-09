package com.example.ad42_dialog_menu_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ad42_dialog_menu_demo.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding  binding;
    int yearR,monthOfYear,day,h,m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        Calendar calendar= Calendar.getInstance();
        day= calendar.get(Calendar.DAY_OF_MONTH);
        monthOfYear= calendar.get(Calendar.MONTH);
        yearR= calendar.get(Calendar.YEAR);
        binding.tvDate.setText(day+"-"+(monthOfYear+1)+"-"+yearR);
        binding.tvTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings=getResources().getStringArray(R.array.tags);
                boolean[] booleans= {true,true,false,false,false,true};
                AlertDialog alertDialog= new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Choose Tags")
                        .setMultiChoiceItems(strings, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                binding.tvWeeks.setText("");
                                StringBuilder builder= new StringBuilder();
                                for(int i=0;i<strings.length;i++){
                                    if(booleans[i])
                                        //binding.tvWeeks.setText(binding.tvWeeks.getText().toString()+strings[i]);
                                        builder.append(strings[i]+",");
                                }
                                int length= builder.length();
                                builder.replace(length-1,length,"");
                                binding.tvTags.setText(builder.toString());



                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                binding.tvTags.setText("");



                            }
                        }).create();
                alertDialog.show();
            }
        });
        binding.tvWeeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings= getResources().getStringArray(R.array.weeks);
                boolean[] booleans={true,true,true,false,false,false,true};
                AlertDialog alertDialog= new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Choose tags :")
                        .setMultiChoiceItems(strings, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                binding.tvWeeks.setText("");
                                StringBuilder builder= new StringBuilder();
                                for(int i=0;i<strings.length;i++){
                                    if(booleans[i])
                                        //binding.tvWeeks.setText(binding.tvWeeks.getText().toString()+strings[i]);
                                        builder.append(strings[i]+",");
                                }
                                int length= builder.length();
                                builder.replace(length-1,length,"");
                                binding.tvWeeks.setText(builder.toString());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                binding.tvTags.setText("");
                            }
                        }).create();
                alertDialog.show();
            }
        });
        binding.btnTune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu= new PopupMenu(getBaseContext(),v);
                MenuInflater menuInflater= popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.popupmenu_tune,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mnTuneFromdefaults:
                                String[] strings = getResources().getStringArray(R.array.tunes);
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("")
                                        .setSingleChoiceItems(strings, 2, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ListView lw = ((AlertDialog)dialog).getListView();
                                                Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                                                binding.tvTune.setText(checkedItem.toString().trim());

                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).create();
                                alertDialog.show();
                                break;
                            case R.id.mnTuneFromfile:
                                StringBuilder stringBuilder = new StringBuilder();
                                File sdcard = Environment.getExternalStorageDirectory();
                                File file = new File(sdcard,"file.txt");
                                try {
                                    BufferedReader br = new BufferedReader(new FileReader(file));
                                    String line;

                                    while ((line = br.readLine()) != null) {
                                        stringBuilder.append(line+", ");

                                    }
                                    br.close();
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if(!stringBuilder.equals(""))
                                binding.tvTune.setText(stringBuilder.toString());
                                else binding.tvTune.setText("0");

                                break;
                        }
                        return true;
                    }
                });
            }
        });

        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        binding.tvDate.setText(dayOfMonth+""+"-"+(month+1)+""+"-"+year+"");
                        day=dayOfMonth;monthOfYear=month;yearR=year;
                    }
                };
                DatePickerDialog datePickerDialog= new DatePickerDialog(MainActivity.this,dateSetListener,yearR,monthOfYear,day);
                datePickerDialog.show();
            }
        });
        h=calendar.get(Calendar.HOUR_OF_DAY);
        m=calendar.get(Calendar.MINUTE);
        binding.tvTime.setText(h+":"+m);
        binding.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener timeSetListener= new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            binding.tvTime.setText(hourOfDay+":"+minute);
                            h=hourOfDay;m=minute;
                    }
                };
                TimePickerDialog timePickerDialog= new TimePickerDialog(MainActivity.this,timeSetListener,h,m,true);
                timePickerDialog.show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.mnCancel:
                    Toast.makeText(getBaseContext(),"Canceled",Toast.LENGTH_LONG).show();
                    break;
                //case R.id.mnTuneFromdefaults:

                default: break;
            }
        return super.onOptionsItemSelected(item);
    }
    public String getDate(){
        Calendar calendar= Calendar.getInstance();
        int year= calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        return day+"-"+month+"-"+year;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= new MenuInflater(getBaseContext());
        menuInflater.inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

}