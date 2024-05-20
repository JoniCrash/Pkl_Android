package com.example.layout
//
//
//val listNote = remember {
//    mutableStateListOf<Noted>()
//}
//
//var showDialog by remember {
//    mutableStateOf(false)
//}
//
//var title by remember {
//    mutableStateOf("")
//}
//
//var deskripsi by remember {
//    mutableStateOf("")
//}
//LayoutTheme {
////                window.statusBarColor = getColor(R.color.orange)
////                window.navigationBarColor = getColor(R.color.orange)
//    val showMenu = remember { mutableStateOf(false) }
//
//    TopAppBar(title = {
//        Text(text = "Dashboard")
//    },
//        colors = TopAppBarDefaults.topAppBarColors(Color.Blue),
//        navigationIcon = {
//            IconButton(onClick = { showMenu.value = true }) {
//                Icon(
//                    painterResource(id = R.drawable.baseline_menu_24),
//                    contentDescription = null
//                )
//                // Menggunakan Garis 3
//            }
//            DropdownMenu(
//                expanded = showMenu.value,
//                onDismissRequest = { showMenu.value = false }) {
//                DropdownMenuItem(
//                    text = {
//                        Text(text = "Emang boleh?")
//                    },
//                    onClick = { /*TODO*/ })
//                DropdownMenuItem(
//                    text = {
//                        Text(text = "Emang boleh??")
//                    },
//                    onClick = { /*TODO*/ })
//                DropdownMenuItem(
//                    text = {
//                        Text(text = "Emang boleh????")
//                    },
//                    onClick = { /*TODO*/ })
//                DropdownMenuItem(
//                    text = {
//                        Text(text = "Emang boleh????")
//                    },
//                    onClick = { /*TODO*/ })
//            }
//            //akhir garis 3
//
//        }
////                    ,actions = {
////                        IconButton(onClick = { showMenu.value = true }) {
////                            Icon(
////                                painterResource(id = R.drawable.baseline_more_vert_24),
////                                contentDescription = null
////                            )
////                        }
////                        DropdownMenu(
////                            expanded = showMenu.value,
////                            onDismissRequest = { showMenu.value = false }) {
////                            DropdownMenuItem(
////                                text = {
////                                    Text(text = "Emang boleh?")
////                                },
////                                onClick = { /*TODO*/ })
////                            DropdownMenuItem(
////                                text = {
////                                    Text(text = "Emang boleh??")
////                                },
////                                onClick = { /*TODO*/ })
////                            DropdownMenuItem(
////                                text = {
////                                    Text(text = "Emang boleh????")
////                                },
////                                onClick = { /*TODO*/ })
////                            DropdownMenuItem(
////                                text = {
////                                    Text(text = "Emang boleh????")
////                                },
////                                onClick = { /*TODO*/ })
////                        }
////                    }
//    )
//
//
//    if (showDialog) {
//        Dialog(onDismissRequest = {
//            showDialog = false
//        }) {
//            Card(
//                Modifier.fillMaxWidth().wrapContentHeight(),
//            ) {
//                Column(Modifier.padding(16.dp)) {
//                    TextField(
//                        value = title,
//                        label = {
//                            Text(text = "Title")
//                        },
//                        placeholder = {
//                            Text(text = "Title")
//                        },
//                        onValueChange = { v ->
//                            title = v
//                        },
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                    TextField(
//                        value = deskripsi,
//                        label = {
//                            Text(text = "Deskripsi")
//                        },
//                        placeholder = {
//                            Text(text = "Deskripsi")
//                        },
//                        onValueChange = { v ->
//                            deskripsi = v
//                        },
//                    )
//
//                    Spacer(modifier = Modifier.height(20.dp))
//                    Row(Modifier.fillMaxWidth()) {
//                        Button(onClick = {
//                            showDialog = false
//                        }) {
//                            Text(text = "Cancel")
//                        }
//
//                        Spacer(modifier = Modifier.width(20.dp))
//                        Button(onClick = {
//                            showDialog = false
//                            val note = Noted(
//                                title,
//                                deskripsi,
//                            )
//
//                            listNote.add(note)
//
//                            title = ""
//                            deskripsi = ""
//                        }) {
//                            Text(text = "Save")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
////ccc
//
//Surface(
//modifier = Modifier.fillMaxSize(),
//color = MaterialTheme.colorScheme.background,
//) {
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                showDialog = true
//            }) {
//                Icon(Icons.Default.Add, contentDescription = null)
//            }
//        },
//    ) {
//        Column(
//            Modifier.padding(it).padding(16.dp),
//        ) {
//            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                items(listNote.size, key = { it }) { index ->
//                    Card(
//                        Modifier.fillMaxWidth()
//                            .clickable {
//                                showDialog = true
//
//                                title = listNote[index].title
//                                deskripsi = listNote[index].deskripsi
//                            },
//                    ) {
//                        Row(Modifier.fillMaxWidth()) {
//                            Column(
//                                Modifier.padding(8.dp)
//                                    .weight(1f),
//                            ) {
//                                Text(text = listNote[index].title)
//                                Spacer(modifier = Modifier.height(16.dp))
//                                Text(text = listNote[index].deskripsi)
//                            }
//                            Spacer(modifier = Modifier.width(5.dp))
//                            IconButton(onClick = {
//                                listNote.removeAt(index)
//                            }) {
//                                Icon(
//                                    Icons.Default.Delete,
//                                    contentDescription = null,
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
////ccc