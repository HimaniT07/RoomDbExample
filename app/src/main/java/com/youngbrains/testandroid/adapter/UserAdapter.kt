

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngbrains.testandroid.databinding.UserItemXmlBinding
import com.youngbrains.testandroid.model.User

class UserAdapter(val userItem: ArrayList<User>, param: Any) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    class MyViewHolder(var binding: UserItemXmlBinding) :RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(UserItemXmlBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.userValue = userItem[position]
    }

    override fun getItemCount(): Int {
        return userItem.size
    }
}