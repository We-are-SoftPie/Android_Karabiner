package com.softpie.karabiner.ui.root

import com.softpie.karabiner.R

sealed class NavGroup(val group: String) {
    sealed class Auth(val id: String) : NavGroup("auth") {
        object LAUNCH: Auth("launch")
        object NAME: Auth("name")
        object TEL: Auth("tel")
        object EMAIL: Auth("email")
        object COMPLETE: Auth("complete")
    }

    sealed class Main(val id: String, val icon: Int) : NavGroup("main") {
        object MAIN: Main("main", -1)
        object LIST: Main("list", R.drawable.ic_list)
        object CAM: Main("cam", R.drawable.ic_cam)
        object SET: Main("my", R.drawable.ic_set)
        object LIST_IFNO: Main("homeInfo/{id}", R.drawable.ic_list)
        object TEST: Main("ee", -1)
    }

//    sealed class Feature(val id: String, val title: String) : NavGroup("feature") {
//        object BOARD: Feature("board", "게시글")
//        object POST: Feature("post", "글쓰기")
//    }
}