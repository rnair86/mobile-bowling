package com.challenge.bowlinggame_shared.datastructures

class BiNode<T>(value:T) {
    var value:T=value
    var priorNode:BiNode<T>?=null
    var nextNode:BiNode<T>?=null
}