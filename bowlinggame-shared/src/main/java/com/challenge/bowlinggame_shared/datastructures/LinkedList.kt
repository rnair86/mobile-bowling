package com.challenge.bowlinggame_shared.datastructures

class LinkedList<T> {
    private var headNode:BiNode<T>?=null
    var isEmpty:Boolean= headNode==null

    fun firstNode():BiNode<T>?=headNode

    fun lastNode():BiNode<T>?{
        var node=headNode
        if(node!=null){
            while (node?.nextNode !=null){
                node= node.nextNode
            }
            return node
        }else{
            return null
        }
    }

    fun count():Int{
        var node=headNode
        if(node!=null){
            var counter=1
            while (node?.nextNode !=null){
                node= node.nextNode
                counter++
            }
            return counter
        }else{
            return 0
        }
    }

    fun append(value:T){
        var node = BiNode(value)
        var lastNode=this.lastNode()
        if(lastNode!=null){
            node.priorNode=lastNode
            lastNode.nextNode=node
        }else{
            headNode=node
        }
    }

    fun clear(){
        headNode=null
    }

    fun findAtIndex(index:Int):BiNode<T>?{
        val size=this.count()
        if(index in 0 until size){
            var node=headNode
            var i =index
            while (node!=null){
                if(i==0){
                    return node
                }
                i-=1
                node=node.nextNode
            }
        }
        return null
    }

    fun popNode(node: BiNode<T>):T {
        val prev = node.priorNode
        val next = node.nextNode
        if (prev != null) {
            prev.nextNode = next
        } else {
            headNode = next
        }
        next?.priorNode = prev
        node.priorNode = null
        node.nextNode = null
        return node.value
    }

    fun popLastNode():T?{
        val lastNode=this.lastNode()
        if(lastNode!=null){
            return popNode(lastNode)
        }else{
            return null
        }
    }

    fun popAtIndex(index: Int):T? {
        val node = findAtIndex(index)
        if (node != null) {
            return popNode(node)
        } else {
            return null
        }
    }


    override fun toString(): String {
        var s = "["
        var node = headNode
        while (node != null) {
            s += "${node.value}"
            node = node.nextNode
            if (node != null) { s += ", " }
        }
        return s + "]"
    }

}