package com.example.extraordinarios_jrm;

import static com.example.extraordinarios_jrm.R.layout.activity_register;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etMatricula, etNombre, etApellidoPaterno, etApellidoMaterno, etEdad, etGrupo, etPassword, etConfirmPassword;
    private RadioGroup rgSexo;
    private RadioButton rbMasculino, rbFemenino;
    private Spinner spCarrera;
    private Button btnSave;
    private String sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_register);

        etMatricula = findViewById(R.id.etMatricula);
        etNombre = findViewById(R.id.etNombre);
        etApellidoPaterno = findViewById(R.id.etApellidoPaterno);
        etApellidoMaterno = findViewById(R.id.etApellidoMaterno);
        etEdad = findViewById(R.id.etEdad);
        etGrupo = findViewById(R.id.etGrupo);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        rgSexo = findViewById(R.id.rgSexo);
        rbMasculino = findViewById(R.id.rbMasculino);
        rbFemenino = findViewById(R.id.rbFemenino);

        spCarrera = findViewById(R.id.spCarrera);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.carreras_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCarrera.setAdapter(adapter);

        rgSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbMasculino) {
                    sexo = "Masculino";
                } else if (checkedId == R.id.rbFemenino) {
                    sexo = "Femenino";
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudentData();
            }
        });
    }

    private void saveStudentData() {
        String matricula = etMatricula.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellidoPaterno = etApellidoPaterno.getText().toString();
        String apellidoMaterno = etApellidoMaterno.getText().toString();
        String edad = etEdad.getText().toString();
        String grupo = etGrupo.getText().toString();
        String carrera = spCarrera.getSelectedItem().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (password.equals(confirmPassword)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://mvptest.me/extraordinarios_JRM/guardar_estudiante.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, "Ocurrió un error. Inténtelo más tarde", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("matricula", matricula);
                    params.put("nombre", nombre);
                    params.put("apellidoPaterno", apellidoPaterno);
                    params.put("apellidoMaterno", apellidoMaterno);
                    params.put("sexo", sexo);
                    params.put("edad", edad);
                    params.put("grupo", grupo);
                    params.put("carrera", carrera);
                    params.put("password", password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            // Mensaje de éxito
            Toast.makeText(RegisterActivity.this, "El estudiante fue almacenado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }
}
