package com.libdumper.dumper

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Process (
    var processpath:String?=null,
    var processname: String?=null,
    var sAddress: Long = 0L,
    var eAddress: Long = 0L,
    var size:Long = 0L
) : Parcelable{
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.apply {
            writeString(processpath)
            writeString(processname)
            writeLong(sAddress)
            writeLong(eAddress)
            writeLong(size)

        }
    }
    companion object{
        @JvmField
        val CREATOR: Parcelable.Creator<Process> = object : Parcelable.Creator<Process> {

            override fun createFromParcel(source: Parcel): Process {
                val process = Process()
                process.processpath = source.readString()
                process.processname = source.readString()
                process.sAddress = source.readLong()
                process.eAddress = source.readLong()
                process.size = source.readLong()
                return process
            }

            override fun newArray(size: Int): Array<Process> {
                return newArray(size)
            }

        }
    }
}