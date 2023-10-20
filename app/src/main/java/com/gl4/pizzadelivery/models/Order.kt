package com.gl4.pizzadelivery.models

public enum class Size {
    NO_SIZE,
    SMALL,
    MEDIUM,
    BIG
}
public enum class Supplement {
    CHAMPIGNON,
    FROMAGE,
    NEPTUNE
}
fun sizeToString(size : Size) : String {
    if (size == Size.SMALL) return "small"
    if (size == Size.MEDIUM) return "medium"
    if (size == Size.BIG) return "big"
    if (size == Size.NO_SIZE) return "no size"
    return "error"
}
fun supplementToString(supplements : List<Supplement>) : String {
    var supplementsAsString : String = ""
    for (supplement in supplements) {
        if(supplement == Supplement.CHAMPIGNON) supplementsAsString += "champignon "
        if(supplement == Supplement.FROMAGE) supplementsAsString += "fromage "
        if(supplement == Supplement.NEPTUNE) supplementsAsString += "neptune "
    }
    return supplementsAsString
}
val basePrice : Map<Size, Int> = mapOf(
    Size.NO_SIZE to 0 ,
    Size.SMALL to 6, Size.MEDIUM to 9 , Size.BIG to 12)
val supplementsPrice : Map<Supplement, Int> = mapOf(Supplement.FROMAGE to 5 , Supplement.CHAMPIGNON to 3 , Supplement.NEPTUNE to 6)

public class Order {
    private var firstName : String = ""
    private var lastName : String = ""
    private var address : String = ""
    private var size : Size = Size.NO_SIZE
    private var price : Int = 0
    private var supplement : List<Supplement> =  emptyList<Supplement>()
    fun getPrice() : Int = this.price
    fun setFirstName(value : String){
        this.firstName = value
    }
    fun setLastName(value : String){
        this.lastName = value
    }
    fun setAddress(value : String){
        this.address = value
    }
    fun setSize(value : Size){
        this.price -= basePrice[this.size] ?: 0
        this.size = value
        this.price += basePrice[this.size] ?: 0
    }
    fun toggleSupplement(value : Supplement) {
        if (this.supplement.contains(value)) {
            this.undoSupplement(value)
        }else{
            this.addSupplement(value)
        }
    }
    private fun addSupplement(value : Supplement){
        this.supplement += value
        this.price += supplementsPrice[value] ?: 0
    }
    private fun undoSupplement(value : Supplement){
        this.supplement -= value
        this.price -= supplementsPrice[value] ?: 0
    }
    fun isComplete() : String {
        if(this.firstName == "") return "first name"
        if(this.lastName == "") return "last name"
        if(this.address == "") return "address"
        if(this.size == Size.NO_SIZE) return "size"
        return "OK"
    }
    fun orderMessage() : String {
        return "Merci monsieur ${this.firstName} ${this.lastName} pour avoir commandé une Pizza , votre pizza est de taille ${sizeToString(this.size)} avec les supplements ${supplementToString(this.supplement)} , le prix de votre pizza est ${this.price}"
    }
    fun orderMail() : String {
        return "Monieur ${this.firstName} ${this.lastName} à commandé une pizza ${sizeToString(this.size)} avec les supplement ${supplementToString(this.supplement)}"
    }
    fun getAddress() : String = this.address
}