package com.example.cachelite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnClearOwnCache = findViewById<Button>(R.id.btnClearOwnCache)
        btnClearOwnCache.setOnClickListener {
            clearOwnCache()
        }

        setupAppList()
    }

    private fun clearOwnCache() {
        try {
            val cacheDir: File = cacheDir
            deleteDir(cacheDir)
            Toast.makeText(this, "¡Caché propia limpiada correctamente!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al limpiar la caché", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            if (children != null) {
                for (child in children) {
                    val success = deleteDir(File(dir, child))
                    if (!success) return false
                }
            }
        }
        return dir?.delete() ?: false
    }

    private fun setupAppList() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewApps)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val pm = packageManager
        val packages = pm.getInstalledApplications(0)

        val appList = mutableListOf<AppModel>()
        for (packageInfo in packages) {
            val appName = pm.getApplicationLabel(packageInfo).toString()
            val packageName = packageInfo.packageName
            val icon = pm.getApplicationIcon(packageInfo)

            appList.add(AppModel(appName, packageName, icon))
        }

        // Ordenar alfabéticamente
        appList.sortBy { it.appName }

        val adapter = AppAdapter(appList) { packageName ->
            openAppSettings(packageName)
        }
        recyclerView.adapter = adapter
    }

    private fun openAppSettings(packageName: String) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
}
