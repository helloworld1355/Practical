<template>
	<view class="content">
		<image class="top_image" mode="aspectFill" :src="markers[0].iconPath"></image>
		<hr class="hr1">
		<view class="article" v-for="(item,index) in markers">
			
			<image class="art-image" :src="item.iconPath" ></image>
			<text> {{scenes[index].content}} </text>
			<hr style="margin-top: 10px;border-top: 2px dashed blue;" class="hr1">
		</view>
		<button @click="log">log-scenes</button>
	</view>
</template>

<script>
	import base from '../../utils/base.js'
	export default {
		data() {
			return {
				scenes:[],
				markers:[],
			}
		},
		onLoad(){
			this.check();
		},
		methods: {
			check :function (){
				let that=this;
				// 获取本地缓存中的数据
				that.markers = wx.getStorageSync('markers');
				that.scenes = wx.getStorageSync('scenes');
				console.log("origin:",that.scenes);
				
				const hash = require('hash.js');
				
				//计算哈希值函数
				function calculateHash(data) {
				  // 使用 SHA-256 算法计算哈希值
				    const sha256 = hash.sha256();
				    sha256.update(JSON.stringify(data));
					console.log(sha256.digest('hex'));
				    return sha256.digest('hex');
				}
				
				// console.log("进入缓存:markers:"+wx.getStorageSync('markers'))
				// 发送请求获取服务器最新数据
				wx.request({
				  url: base.host+'markers/getAll',
				  success: function (res) {
				    const serverData = res.data.data;
					// console.log(serverData);
					 console.log("stroage:", wx.getStorageSync('markers'))
				
				    // 计算哈希值并比较
					const localHash = wx.getStorageSync('markersHash') || 0;
					const serverHash = calculateHash(serverData);
				
					if (localHash !== serverHash) {
					  // 更新缓存和哈希值
					 
					  wx.setStorageSync('markers', serverData);
					  wx.setStorageSync('markersHash', serverHash);
					  console.log('markers:缓存已更新');
				    } else {
				      console.log('缓存未变化');
				    }
				  },
				  fail: function (error) {
				    console.log('获取服务器数据失败', error);
				  }
				});
				
				wx.request({
				  url: base.host+'scene/getAll',
				  success: function (res) {
				    const serverData = res.data.data;
				
				    // 计算哈希值并比较
				    const localHash = wx.getStorageSync('sceneHash') || 0;
				    const serverHash = calculateHash(serverData);
				    				
				    if (localHash !== serverHash) {
				      // 更新缓存和哈希值
				      wx.setStorageSync('scenes', serverData);
				      wx.setStorageSync('sceneHash', serverHash);
				      console.log('scenes:缓存已更新');
				    } else {
				      console.log('缓存未变化');
				    }
				  },
				  fail: function (error) {
				    console.log('获取服务器数据失败', error);
				  }
				});
				
				
				
			},
			log :function(){
				console.log("scenes::",this.scenes);
			},
		}
	}
</script>

<style>
.content{
	height: calc(100vh);
	width: calc(100vh);
	
}
.top_image{
	width: calc(100);
	height: 35%;
	margin-bottom: 5px;
}
.article{
	width: calc(100);
	height: 200rpx;
	padding: 10px 0;
	display: flex;
}
hr1{
	border: 0;
	border-top: 2px dashed blue;
}
.art-image{
	width: 20%;
	height: 100%;
	margin-right: 10px;
}
.art-text{
	width: 80%;
	height: 100%;
}
</style>
