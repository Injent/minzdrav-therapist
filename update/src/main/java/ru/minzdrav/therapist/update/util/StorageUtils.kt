package ru.minzdrav.therapist.update.util

import android.os.Environment
import android.os.StatFs

internal fun availableSpace(): Long {
    val stat = StatFs(Environment.getExternalStorageDirectory().path)
    return stat.blockSizeLong * stat.availableBlocksLong
}