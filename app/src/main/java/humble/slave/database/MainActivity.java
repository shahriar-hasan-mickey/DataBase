package humble.slave.database;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import humble.slave.database.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        
        DB = new DataBase(this);

        switchState("00");
        onChangeState(binding.toggle, "00");
        insertData(binding.insert);
        updateData(binding.update);
        deleteData(binding.delete);
        viewData(binding.showData);
    }

    private void onChangeState(Switch toggle, String id) {
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Cursor response = DB.getSwitchState(id);
                Boolean checkStateUpdateData;
                if(response.getCount()==1) {
                    if(toggle.isChecked()) {
                        checkStateUpdateData = DB.updateUserData(id, "ON", null);
                    }else{
                        checkStateUpdateData = DB.updateUserData(id, "OFF", null);
                    }
                }else{
                    if(toggle.isChecked()) {
                        checkStateUpdateData = DB.insertUserData(id, "ON", null);
                    }else{
                        checkStateUpdateData = DB.insertUserData(id, "OFF", null);
                    }
                }
                if(checkStateUpdateData){
                    Toast.makeText(MainActivity.this, "SUCCESSFUL INSERT/UPDATE", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "UNSUCCESSFUL INSERT/UPDATE", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void switchState(String id){
        Cursor response = DB.getSwitchState(id);
        if(response.getCount()==0){
            Toast.makeText(this, "NOT SET", Toast.LENGTH_SHORT).show();
        }else{
            response.moveToNext();
            if(response.getString(1).equals("ON")){
                binding.toggle.setChecked(true);
            } else if (response.getString(1) == "OFF") {
                binding.toggle.setChecked(false);
            }
        }
    }

    public void insertData(Button button){
        button.setOnClickListener(view -> {
            Boolean checkInsertData = DB.insertUserData(
                    binding.input1.getText().toString(),
                    binding.input2.getText().toString(),
                    binding.input3.getText().toString());
            if(checkInsertData){
                Toast.makeText(MainActivity.this, "INSERT SUCCESS", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "INSERT FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateData(Button button){
        button.setOnClickListener(view -> {
            Boolean checkUpdateData = DB.updateUserData(
                    binding.input1.getText().toString(),
                    binding.input2.getText().toString(),
                    binding.input3.getText().toString());
            if(checkUpdateData){
                Toast.makeText(MainActivity.this, "UPDATE SUCCESS", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "UPDATE FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deleteData(Button button){
        button.setOnClickListener(view -> {
            Boolean checkDeleteData = DB.deleteUserData(
                    binding.input1.getText().toString()
            );
            if(checkDeleteData){
                Toast.makeText(MainActivity.this, "DELETE SUCCESS", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "DELETE FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void viewData(Button button){
        button.setOnClickListener(view -> {
            Cursor response = DB.getUserData();
            if(response.getCount()==0){
                Toast.makeText(MainActivity.this, "NO DATA EXISTS", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while(response.moveToNext()){
                buffer.append("INPUT1 : ").append(response.getString(0)).append("\n");
                buffer.append("INPUT2 : ").append(response.getString(1)).append("\n");
                buffer.append("INPUT3 : ").append(response.getString(2)).append("\n\n");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("User Entries");
            builder.setMessage(buffer.toString());
            builder.show();
        });

    }
}