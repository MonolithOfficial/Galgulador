package ge.edu.btu.galgulador

import android.content.Context
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM
import androidx.core.widget.TextViewCompat
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    //ტექსტვიუ, რომელზეც შეტანილი რიხცვები და შედეგი გამოისახება
    lateinit var calcField: TextView

    //არის თუ არა ბოლო შეტანილი (დაჭერილი) სიმბოლო ციფრი
    var lastNumeric: Boolean = false

    //შეცდომის სტატუსი
    var stateError: Boolean = false

    //თუ სიმართლეა, მეორე წერტილი (ათწილადისა) აღარ შეიტანება.
    var lastDot: Boolean = false

    var lastRoot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calcField = findViewById(R.id.calcField)
        init()
    }

    //ვამატებთ ღილაკის ტექსტს ტექსტვიუს ბოლოში (append)
    fun onDigit(view: View){
        if (stateError){
            //თუ შეცდომაა, იწერება შეცდომის E ტექსტი
            calcField.text = (view as Button).text
            stateError = false
        }else{
            //თუ შეცდომა არ არის, გამოსახულებას ვამატებთ ტექსტვიუს ბოლოში
            calcField.append((view as Button).text)
        }
        //ბოლო სიმბოლო ციფრია
        lastNumeric = true
        val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibratorService.vibrate(30)
        //წელვადი ფონტის ზომა
        TextViewCompat.setAutoSizeTextTypeWithDefaults(calcField, AUTO_SIZE_TEXT_TYPE_UNIFORM )
    }

    //ვამატებთ წერტილს ტექსტვიუში
    fun onDecimalPoint(view: View){
        if(lastNumeric && !stateError && !lastDot){
            calcField.append(".")
            lastNumeric = false
            lastDot = true
        }
    }



    //ვამატებთ ოპერატორებს ტექსტვიუში
    fun onOperator(view: View){
        if (lastNumeric && !stateError){
            calcField.append((view as Button).text)
            lastNumeric = false
            lastDot = false //ვარესეტებთ წერტილის ნიშნულს. ის ბოლო არ არის.
        }
        val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibratorService.vibrate(20)
    }

    private fun backSpace(){
        if(calcField.text.isNotEmpty()){
            calcField.text = calcField.text.toString().substring(0, calcField.text.toString().length - 1)
        }
    }

    //ტექსტვიუს გათავისუფლება
    private fun init(){
        btnDel.setOnLongClickListener {
            this.calcField.text = ""
            lastNumeric = false
            stateError = false
            lastDot = false
            upperField.text = ""
            true
        }
        btnDel.setOnClickListener{
            backSpace()
        }
    }



    //მათემატიკური ოპერაციები ExpressionBuilder-ის დახმარებით
    fun onEqual(view: View){
        //თუ ვართ შეცდომის სტატუსზე, პროგრამა აღარაფერს აკეთებს
        //თუ ბოლო შეყვანილი სიმბოლო არის ციფრი, პასუხი მოიძებნება
        if (lastNumeric && !stateError){
            //გამოსახულების წაკითხვა
            val txt = calcField.text.toString()
            //exp4j კრავს გამოსახულებას შეტანილი სტრინგისგან
            val expression = ExpressionBuilder(txt).build()
            try {
                //გაანგარიშება და პასუხის გამოტანა
                val result = expression.evaluate()
                upperField.append(calcField.text.toString() + "=")
                calcField.text = result.toString()
                lastDot = true //პასუხი შეიცავს წერთილს, ანუ არის ათწილადი (float)
            }catch (ex: ArithmeticException){
                //*******ჭმუნვის შეძახილი*********
                calcField.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

}
