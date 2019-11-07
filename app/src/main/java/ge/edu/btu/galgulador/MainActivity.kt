package ge.edu.btu.galgulador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        btnOne.setOnClickListener {
            addNumber(btnOne)
        }
        btnTwo.setOnClickListener {
            addNumber(btnTwo)
        }
        btnThree.setOnClickListener {
            addNumber(btnThree)
        }
        btnFour.setOnClickListener {
            addNumber(btnFour)
        }
        btnFive.setOnClickListener {
            addNumber(btnFive)
        }
        btnSeven.setOnClickListener {
            addNumber(btnSeven)
        }
        btnEight.setOnClickListener {
            addNumber(btnEight)
        }
        btnNine.setOnClickListener {
            addNumber(btnNine)
        }
        btnZero.setOnClickListener {
            addNumber(btnZero)
        }
        btnDot.setOnClickListener {
        }
        btnEquals.setOnClickListener {
        }
        btnDel.setOnClickListener {
            delete()
        }
        btnDiv.setOnClickListener {
        }
        btnMultiply.setOnClickListener {
        }
        btnSub.setOnClickListener {
        }
        btnAdd.setOnClickListener {
        }
    }

    private fun addNumber(button: Button){
        calcField.text = calcField.text.toString() + button.text.toString()
    }
    private fun delete(){
        if (calcField.text.isNotEmpty()){
            calcField.text = calcField.text.toString().substring(0,calcField.text.toString().length -1)
        }
    }
}
