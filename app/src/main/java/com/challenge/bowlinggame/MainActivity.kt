package com.challenge.bowlinggame

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.bowlinggame.adapters.GameFrameAdapter
import com.challenge.bowlinggame.bl.GameManager
import com.challenge.bowlinggame.bl.IGameManager
import com.challenge.bowlinggame.bl.IGameStateListner
import com.challenge.bowlinggame_shared.GameFrame
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val validEntries= listOf("STRIKE","SPARE","MISS","1","2","3","4","5","6","7","8","9")

    private lateinit var gameManager:IGameManager
    private lateinit var gameState:List<GameFrame>
    private lateinit var currentFrame:GameFrame
    private lateinit var gameadapter:GameFrameAdapter

    private val TAG = this::class.simpleName

    //pri


    private val gameStateListner:IGameStateListner = object :IGameStateListner{
        override fun onGameStateUpdate(framelist: List<GameFrame>) {
            gameState=framelist
            setCurrentFrame()
        }

        override fun onError(error: Throwable) {
            val message=error.localizedMessage
            textEntryError(message)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewsinit()
        gameManager=GameManager(gameStateListner)
        gameState=gameManager.getGameState()
        setCurrentFrame()
        gameadapter=GameFrameAdapter(gameState)

        game_list_view.apply {
            layoutManager=GridLayoutManager(this@MainActivity,5)
            adapter=gameadapter
        }
    }

    private fun viewsinit(){
        btnsend.setOnClickListener {
            btnsend_Clicked()
        }
        btnNewGame.setOnClickListener {
            btnNewGame_Clicked()
        }

        txtRoll.editText?.text?.clear()
        txtRoll.editText?.doOnTextChanged { text, start, before, count ->
            if(!txtRoll.error.isNullOrEmpty()){
                txtRoll.error = null
            }
        }


    }
    private fun setCurrentFrame(){
        if(gameState.any()){
            currentFrame=gameState.last()
            updateUX()
        }
    }

    private fun btnsend_Clicked(){
        //validateText()
        val enteredroll= txtRoll.editText?.text.toString()
        if(validateText(enteredroll)){
            //Toast.makeText(this,enteredroll,Toast.LENGTH_LONG).show()
            gameManager.calculateRoll(enteredroll)
        }else{
            val message="The Entry $enteredroll is not valid"
            textEntryError(message)
        }
        txtRoll.editText?.text?.clear()

    }

    private fun btnNewGame_Clicked(){

        gameState=gameManager.makenewGame()
        setCurrentFrame()
        updateUX()
    }

    private fun validateText(text:String):Boolean{
        if(validEntries.contains(text.toUpperCase().trim())){
            return true
        }
        return false
    }

    private fun updateUX(){

        lifecycleScope.launch (Dispatchers.Main) {
            if(currentFrame!=null){
                txtFrameno.text=currentFrame.number.toString()
                txtTurnsLeft.text=currentFrame.remainingRolls.toString()
            }else{
                txtFrameno.text=""
                txtTurnsLeft.text=""
            }
            txtScore.text=getCurrentScore().toString()

            gameadapter.setlist(gameState)
        }
    }

    private fun getCurrentScore():Int{
        return gameManager.getCurrentScore()
    }

    private fun textEntryError(message:String){
        lifecycleScope.launch (Dispatchers.Main) {
            txtRoll.error=message
            Toast.makeText(this@MainActivity,message,Toast.LENGTH_SHORT).show()
        }
    }
}