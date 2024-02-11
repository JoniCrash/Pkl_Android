package com.example.layout

data class Plist(
    val name: String,
    val image: Int,
    val desc: String
)
data class TList(
    val noHp:Int,
    val email:String,
    val namaLengkap:String,
    val paket:String,
    val lokasi:Int
)
fun gData():List<TList> {
    val tlist = arrayListOf<TList>()
    for (i in 0..0){
        tlist.add(
            TList(
                noHp = 0,
                email = "",
                namaLengkap = "",
                paket = "",
                lokasi = 0
            )
        )
    }
    return tlist
}

fun getPlist():List<Plist>{
    val plist = arrayListOf<Plist>()
    for(i in 0..9){
        plist.add(
            Plist(
                name[i], image[i], desc[i]
//                name = "<NAME>",
//                img = R.drawable.comet_dark,
//                desc = "Android Development"
            )
        )
    }
    return plist
}

val name = listOf(
    "Project1",
    "Project2",
    "Project3",
    "Project4",
    "Project5",
    "Project6",
    "Project7",
    "Project8",
    "Project9",
    "Project10",
)
val image = listOf(
    R.drawable.comet_dark,
    R.drawable.comet_dark,
    R.drawable.comet_dark,
    R.drawable.comet_dark,
    R.drawable.comet_dark,
    R.drawable.comet_dark,
    R.drawable.comet_dark,
    R.drawable.comet_dark,
    R.drawable.comet_dark,
    R.drawable.comet_dark,
)

val desc = listOf(
    "Project yang sangat baik",
    "Project yang sangat baik",
    "Project yang sangat baik",
    "Project yang sangat baik",
    "Project yang sangat baik",
    "Project yang sangat baik",
    "Project yang sangat baik",
    "Project yang sangat baik",
    "Project yang sangat baik",
    "Project yang sangat baik",
)

