package beyzagobel.depo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.dift.ui.SwipeToAction;

public class DepolarRecyclerViewAdapter extends RecyclerView.Adapter<DepolarRecyclerViewAdapter.DepolarViewHolder> {

    private List<Depolar> depolar;

    public DepolarRecyclerViewAdapter(List<Depolar> depolar){
        this.depolar= depolar;
    }

    public class DepolarViewHolder  extends SwipeToAction.ViewHolder<Depolar>{
        private TextView lblDepoAdi;

        DepolarViewHolder(@NonNull View view) {
            super(view);
            lblDepoAdi = (TextView) view.findViewById(R.id.lblDepoAdi); // eklendi

        }
    }

    @NonNull
    @Override
    public DepolarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_element_depolar,parent,false);
        return new DepolarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DepolarViewHolder holder, int position) {
        Depolar depo = depolar.get(position);
        holder.lblDepoAdi.setText("Depo Adi : "+depo.getDepoAdi());
        holder.data = depo ;
    }
    @Override
    public int getItemCount() {
        return depolar.size();
    }


}
