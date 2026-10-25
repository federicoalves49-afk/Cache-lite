package com.example.cachelite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(
    private val appList: List<AppModel>,
    private val onClickSettings: (String) -> Unit
) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgIcon: ImageView = view.findViewById(R.id.imgAppIcon)
        val txtAppName: TextView = view.findViewById(R.id.txtAppName)
        val txtPackageName: TextView = view.findViewById(R.id.txtPackageName)
        val btnOpenSettings: Button = view.findViewById(R.id.btnOpenSettings)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = appList[position]
        holder.txtAppName.text = app.appName
        holder.txtPackageName.text = app.packageName
        holder.imgIcon.setImageDrawable(app.icon)

        holder.btnOpenSettings.setOnClickListener {
            onClickSettings(app.packageName)
        }
        holder.itemView.setOnClickListener {
            onClickSettings(app.packageName)
        }
    }

    override fun getItemCount() = appList.size
}
