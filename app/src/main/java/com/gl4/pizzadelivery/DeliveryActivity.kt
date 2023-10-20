package com.gl4.pizzadelivery

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.gl4.pizzadelivery.models.Order
import com.gl4.pizzadelivery.models.Size
import com.gl4.pizzadelivery.models.Supplement


class DeliveryActivity : AppCompatActivity() {
    private lateinit var logo : ImageView
    private lateinit var firstNameInput : EditText
    private lateinit var lastNameInput : EditText
    private lateinit var addressInput : EditText
    private lateinit var smallPizzaButton : Button
    private lateinit var mediumPizzaButton : Button
    private lateinit var bigPizzaButton : Button
    private lateinit var champignonCheckBox : CheckBox
    private lateinit var fromageCheckBox : CheckBox
    private lateinit var neptuneCheckBox : CheckBox
    private lateinit var price : TextView
    private lateinit var deliverButton : Button
    private var order : Order = Order()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
        logo = findViewById(R.id.logo)
        logo.setImageResource(R.drawable.pizza)
        firstNameInput = findViewById(R.id.firstName)
        lastNameInput = findViewById(R.id.lastName)
        addressInput = findViewById(R.id.address)
        smallPizzaButton = findViewById(R.id.smallSize)
        mediumPizzaButton = findViewById(R.id.mediumSize)
        bigPizzaButton = findViewById(R.id.bigSize)
        champignonCheckBox = findViewById(R.id.champignonSupplement)
        fromageCheckBox = findViewById(R.id.fromageSupplement)
        neptuneCheckBox = findViewById(R.id.neptuneSupplement)
        price = findViewById(R.id.price)
        deliverButton = findViewById(R.id.button)
        smallPizzaButton.setOnClickListener { onSelectSize(Size.SMALL)}
        mediumPizzaButton.setOnClickListener { onSelectSize(Size.MEDIUM)}
        bigPizzaButton.setOnClickListener { onSelectSize(Size.BIG)}
        champignonCheckBox.setOnClickListener { onSelectSupplement(Supplement.CHAMPIGNON) }
        fromageCheckBox.setOnClickListener { onSelectSupplement(Supplement.FROMAGE) }
        neptuneCheckBox.setOnClickListener { onSelectSupplement(Supplement.NEPTUNE)}
        deliverButton.setOnClickListener { deliver() }
    }
    private fun onSelectSize(size: Size) {
        order.setSize(size)
        price.text = order.getPrice().toString()
    }
    private fun onSelectSupplement(supplement: Supplement) {
        order.toggleSupplement(supplement)
        price.text = order.getPrice().toString()
    }
    private fun deliver(){
        order.setFirstName(firstNameInput.text.toString())
        order.setLastName(lastNameInput.text.toString())
        order.setAddress(addressInput.text.toString())
        val orderDefaults : String = order.isComplete()
        if(orderDefaults != "OK"){
            val builder = AlertDialog.Builder(this@DeliveryActivity)
            builder.setTitle("$orderDefaults is missing")
            builder.setMessage("please enter a valid $orderDefaults")
            builder.setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
            println(orderDefaults)
        }else{
            confirm()
        }
    }
    private fun sendEmail() {
        val recipientEmail = "firasmosbahi15@gmail.com"
        val subject = "Pizza delivery"
        val content = order.orderMail()

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "message/rfc822"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, content)

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        } catch (ex: android.content.ActivityNotFoundException) {
        }
    }
//    private fun sendEmail() {
//        val mailto : String = "mailto:firasmosbahi15@gmail.com" +
//        "?cc=" + "firasmosbahi15@gmail.com" +
//                "&subject=" + Uri.encode("Pizza delivery") +
//                "&body=" + Uri.encode(order.orderMail());
//
//        val emailIntent : Intent = Intent(Intent.ACTION_SENDTO);
//        emailIntent.data = Uri.parse(mailto);
//        startActivity(emailIntent)
//    }
    private fun confirm(){
        val builder = AlertDialog.Builder(this@DeliveryActivity)
        builder.setTitle("Order Confirmation")
        builder.setMessage("${order.orderMessage()}")
        builder.setPositiveButton("Confirm") { dialog, which ->
            sendEmail()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") {dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }
}