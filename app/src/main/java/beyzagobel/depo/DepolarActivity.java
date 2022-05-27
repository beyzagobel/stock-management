package beyzagobel.depo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.dift.ui.SwipeToAction;

import java.util.ArrayList;


public class DepolarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<Depolar> depolarArrayList;
    DatabaseReference databaseReferenceDepolar;
    Depolar depolar;
    DepolarRecyclerViewAdapter adapter;
    private SwipeToAction swp;
    private FloatingActionButton depoEkleFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depolar);

        initComponents();
        depolariGetir();
        depoEkleFab();
        swp();
    }


    private void swp() {
        swp = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<Depolar>() {
            @Override
            public boolean swipeLeft(Depolar depo) {
                // güncelleme
                Intent intent = new Intent(DepolarActivity.this, DepoEkleActivity.class);
                intent.putExtra("depoId", depo.getDepoId());
                startActivity(intent);
                return true;
            }

            @Override
            public boolean swipeRight(Depolar depo) {
                // SİLME
                String depoId = depo.getDepoId();
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("depolar").child(depoId);
                DatabaseReference dRurun = FirebaseDatabase.getInstance().getReference("urunler").child(depoId);
                AlertDialog.Builder builder = new AlertDialog.Builder(DepolarActivity.this);
                builder.setTitle("Silmek İStediğinizden Emin Misiniz?");
                builder.setMessage("Deponun Ürünleri Var İse Ürünleride Silinecek!");
                builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dR.removeValue();
                        dRurun.removeValue();
                    }
                });
                builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DepolarActivity.this,"Silme İŞlemi İptal Edildi!",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;
            }
            @Override
            public void onClick(Depolar depo) {
                Intent intent = new Intent(DepolarActivity.this, UrunlerActivity.class);
                intent.putExtra("depoId", depo.getDepoId());
                startActivity(intent);
            }
            @Override
            public void onLongClick(Depolar depo) {}

        });
    }
    private void depoEkleFab() {
        depoEkleFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DepolarActivity.this, DepoEkleActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.eklemenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.done:
                Intent intent = new Intent(DepolarActivity.this, DepoEkleActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void depolariGetir() {
        depolar = new Depolar();
        depolarArrayList = new ArrayList<>();
        databaseReferenceDepolar = FirebaseDatabase.getInstance().getReference().child("depolar");
        databaseReferenceDepolar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    depolar = ds.getValue(Depolar.class);
                    String depoAdi = depolar.getDepoAdi();
                    String depoId = depolar.getDepoId();
                    depolarArrayList.add(new Depolar(depoId, depoAdi));

                    LinearLayoutManager llm = new LinearLayoutManager(DepolarActivity.this);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setHasFixedSize(true);

                    adapter = new DepolarRecyclerViewAdapter(depolarArrayList);
                    recyclerView.setAdapter(adapter);

                    recyclerView.addItemDecoration(new DividerItemDecoration(DepolarActivity.this, LinearLayoutManager.VERTICAL));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void initComponents() {
        recyclerView = findViewById(R.id.recyclerView);
        depoEkleFab = findViewById(R.id.depoEkleFab);
    }
}