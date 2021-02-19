package com.challenge.bowlinggame.bl

import com.challenge.bowlinggame_shared.GameFrame

interface IGameStateListner {
    fun onGameStateUpdate(framelist :List<GameFrame>)
    fun onError(error:Throwable)
}