package com.example.layout

data class Plist(
    val name: String,
    val img: Int,
    val desc: String
)

fun getPlist():List<Plist>{
    val plist = arrayListOf<Plist>()
    for(i in 0..9){
        plist.add(
            Plist(
                names[i], image[i], desc[i]
//                name = "<NAME>",
//                img = R.drawable.comet_dark,
//                desc = "Android Development"
            )
        )
    }
    return plist
}

val names = listOf(
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