package com.example.louage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;



public class login extends AppCompatActivity {

    public static final String KEY_ROLE = "role"; // Constante pour passer le rôle
    private EditText editTextEmail, editTextPassword;
    private TextView signupText;
    private Button loginButton;
    private DatabaseHelper dbHelper;
    private String selectedRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        // Initialisation des vues
        signupText = findViewById(R.id.signupText);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        dbHelper = new DatabaseHelper(this);

        // Récupérer le rôle passé dans l'Intent
        selectedRole = getIntent().getStringExtra(KEY_ROLE);

        // Gestion du clic pour login
        loginButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Validation des champs
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(login.this, "Enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(login.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérification des informations de connexion
            if (checkLogin(email, password, selectedRole)) {
                Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this, interface1_utilisateur.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestion du clic pour signup
        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, register_utilisateur.class);
            intent.putExtra(KEY_ROLE, selectedRole); // Passer le rôle
            startActivity(intent);
        });
    }

    private boolean checkLogin(String email, String password, String role) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String hashedPassword = password; // Méthode pour hacher le mot de passe

        try {
            db = dbHelper.getReadableDatabase();


                String table = DatabaseHelper.TABLE_UTILISATEUR;
                String emailColumn = DatabaseHelper.COLUMN_EMAIL_UTILISATEUR;
                String passwordColumn = DatabaseHelper.COLUMN_MOT_DE_PASSE_UTILISATEUR;
                cursor = db.query(
                        table,
                        null,
                        emailColumn + "=? AND " + passwordColumn + "=?",
                        new String[]{email, hashedPassword},
                        null,
                        null,
                        null
                );


            return cursor != null && cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DB_ERROR", "Error while checking login", e); // Log d'erreur
            return false;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }







}
