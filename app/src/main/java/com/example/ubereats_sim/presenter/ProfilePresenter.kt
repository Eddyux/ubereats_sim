package com.example.ubereats_sim.presenter

import android.content.Context
import com.example.ubereats_sim.model.DataLoader
import com.example.ubereats_sim.model.MenuItem
import com.example.ubereats_sim.model.UserProfile

class ProfilePresenter(private val context: Context) {

    fun getUserProfile(): UserProfile {
        return DataLoader.loadUserProfile(context)
    }

    fun getMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem("👨‍👩‍👧‍👦", "家庭和青少年", "青少年和成人账号"),
            MenuItem("📋", "列表"),
            MenuItem("🚗", "行程"),
            MenuItem("🏷️", "优惠"),
            MenuItem("🎁", "发送礼品"),
            MenuItem("❓", "帮助"),
            MenuItem("👥", "已保存群组"),
            MenuItem("💼", "设置您的商务账户", "自动支付差旅费和餐费"),
            MenuItem("🤝", "合作伙伴奖励"),
            MenuItem("⭐", "Uber One", "到期日期为 3月25日"),
            MenuItem("🔒", "隐私"),
            MenuItem("♿", "无障碍服务"),
            MenuItem("📢", "通讯"),
            MenuItem("🚙", "提供接载或派送服务以赚取收入"),
            MenuItem("🎙️", "语音指令设置"),
            MenuItem("👤", "管理优步账号"),
            MenuItem("⚠️", "过敏设置"),
            MenuItem("ℹ️", "简介")
        )
    }
}
