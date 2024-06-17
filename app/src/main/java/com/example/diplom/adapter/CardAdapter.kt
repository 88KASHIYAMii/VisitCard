package com.example.diplom.adapter

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.model.Card
import java.io.File
import java.io.FileOutputStream

class CardAdapter : RecyclerView.Adapter<CardAdapter.MineHolder>() {

    private var cardList = emptyList<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MineHolder {
        return MineHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.card_element, parent, false)
        )
    }


    override fun onBindViewHolder(holder: MineHolder, position: Int) {
        val cardItem = cardList[position]

        holder.aFirstName.text = cardItem.firstName
        holder.aSecondName.text = cardItem.secondName
        holder.aThirdName.text = cardItem.thirdName

        holder.aPost.text = cardItem.post

        holder.aAddress.text = cardItem.address

        holder.aPhone.text = cardItem.number
        holder.aMail.text = cardItem.mail


        holder.cards_container.setOnClickListener {
            sendPdfViaBluetooth(
                holder.aAddress.context.applicationContext,
                createPdf(holder.aAddress.context, cardItem),
            )
//             val value = CardsFragmentDirections.actionCardsFragmentToUpdateFragment(cardItem)
//            holder.itemView.findNavController().navigate(value)
        }
    }

    fun createPdf(context: Context, cardItem: Card): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = document.startPage(pageInfo)

        val canvas = page.canvas
        val paint = Paint()
        paint.textSize = 12f // Уменьшаем размер текста для умещения всех данных

        val margin = 20f // Отступы от края страницы
        val startX = margin
        var startY = margin + paint.textSize

        with(cardItem) {
            val firstName = "Имя: ${this.firstName}"
            val secondName = "Фамилия: ${this.secondName}"
            val thirdName = "Отчество: ${this.thirdName}"
            val address = "Адрес: ${this.address}"
            val mail = "Почта: ${this.mail}"
            val number = "Номер: ${this.number}"
            val post = "Пост: ${this.post}"

            // Рисуем текст на странице
            canvas.drawText(firstName, startX, startY, paint)
            startY = startY + paint.textSize // Увеличиваем startY
            canvas.drawText(secondName, startX, startY, paint)
            startY = startY + paint.textSize // Увеличиваем startY
            canvas.drawText(thirdName, startX, startY, paint)
            startY = startY + paint.textSize // Увеличиваем startY
            canvas.drawText(address, startX, startY, paint)
            startY = startY + paint.textSize // Увеличиваем startY
            canvas.drawText(mail, startX, startY, paint)
            startY = startY + paint.textSize // Увеличиваем startY
            canvas.drawText(number, startX, startY, paint)
            startY = startY + paint.textSize // Увеличиваем startY
            canvas.drawText(post, startX, startY, paint)
        }

        document.finishPage(page)

        val pdfFile = File(context.getExternalFilesDir(null), "hello_world.pdf")
        val outputStream = FileOutputStream(pdfFile)
        document.writeTo(outputStream)
        document.close()
        outputStream.close()

        return pdfFile
    }

    fun getAllBluetoothDevices(context: Context): BluetoothDevice? {
        // Получаем адаптер Bluetooth
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // Устройство не поддерживает Bluetooth или адаптер недоступен
            return null
        }

        // Получаем список сопряженных устройств
        val pairedDevices: Set<BluetoothDevice>? = if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        } else
            bluetoothAdapter.bondedDevices

        Toast.makeText(context, "${pairedDevices?.map { it.name }}", Toast.LENGTH_SHORT).show()

        // Устройство с заданным именем не найдено
        return null
    }

    // Вспомогательная функция для вывода Toast сообщения
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun sendPdfViaBluetooth(context: Context, pdfFile: File) {
        getAllBluetoothDevices(context)

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                pdfFile
            )
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Subject")
            putExtra(Intent.EXTRA_TEXT, "Body")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(emailIntent, "Send PDF via Bluetooth").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    class MineHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val aFirstName: TextView = itemView.findViewById(R.id.firstNameText)
        val aSecondName: TextView = itemView.findViewById(R.id.secondNameText)
        val aThirdName: TextView = itemView.findViewById(R.id.thirdNameText)

        val aPost: TextView = itemView.findViewById(R.id.postText)

        val aAddress: TextView = itemView.findViewById(R.id.addressText)

        val aPhone: TextView = itemView.findViewById(R.id.phoneText)
        val aMail: TextView = itemView.findViewById(R.id.mailText)

        val cards_container: CardView = itemView.findViewById(R.id.cards_container)
    }

    fun setData(newCardList: List<Card>) {
        this.cardList = newCardList
        notifyDataSetChanged()
    }
}