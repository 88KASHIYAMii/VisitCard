package com.example.diplom

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.findNavController
import com.example.diplom.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkBluetoothPermissions(this)

        // Установка Toolbar в качестве ActionBar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        // Получение NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        // Настройка ActionBar с NavController
        setupActionBarWithNavController(navController)
    }

    // Функция для проверки и запроса всех необходимых разрешений
    fun checkBluetoothPermissions(activity: Activity) {
        val permissionsToRequest = mutableListOf<String>()

        // Проверяем и добавляем разрешение на использование Bluetooth
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.BLUETOOTH)
        }

        // Проверяем и добавляем разрешение на администрирование Bluetooth
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_ADMIN)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.BLUETOOTH_ADMIN)
        }

        // Проверяем и добавляем разрешение на соединение с Bluetooth устройством
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        // Проверяем и добавляем разрешение на доступ к местоположению (для Android 12 и выше)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // Запрашиваем разрешения, если они не были предоставлены
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                permissionsToRequest.toTypedArray(),
                REQUEST_BLUETOOTH_PERMISSIONS
            )
        }
    }

    // Метод для обработки результата запроса разрешений
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_BLUETOOTH_PERMISSIONS -> {
                // Проверяем предоставлены ли все необходимые разрешения
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    // Разрешения предоставлены, можно использовать Bluetooth
                } else {
                    // Одно из разрешений не было предоставлено, обработать эту ситуацию
                }
            }
        }
    }

    // Константа для идентификации запроса разрешений
    private  val REQUEST_BLUETOOTH_PERMISSIONS = 1

    override fun onSupportNavigateUp(): Boolean {
        val nav = findNavController(R.id.fragmentContainerView)
        return nav.navigateUp() || super.onSupportNavigateUp()
    }
}
