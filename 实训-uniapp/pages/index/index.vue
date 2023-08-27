<template>
	<view class="content">
		<map id="map1" :latitude="latitude"  show-location="ture" :longitude="longitude" :markers="markers" scale="18" 
		enable-satellite show-compass>
			<button class="floating-button" @click="stopRadio">
			    <!-- 这里可以放置按钮的内容，比如一个加号图标 -->
			    +
			  </button>
		</map>
		
	</view>
</template>

<script>
import { fail } from 'assert';
import base from '../../utils/base.js'
	export default {
		data() {
			return {
				latitude:39.52643187332011,
				longitude:116.7383107115382  ,
				markers:[],
				scenes:[],
				innerAudioContext:null,
				isPlayed:0,
			}
		},
		onLoad() {
			let  that=this;
			//每隔3秒触发一次计算距离函数
			var interval = setInterval(this.calculateDistances, 3000);
			
			
			//持续监听用户位置
			// uni.startLocationUpdate({
			//   success: res => console.log('开启小程序接收位置消息成功'),
			//   fail: err => console.error('开启小程序接收位置消息失败：', err),
			//   complete: msg => console.log('调用开启小程序接收位置消息 API 完成')
			// });
			// uni.onLocationChange(function (res) {
			// 	console.log('onload纬度：' + res.latitude);
			// 	console.log('onload经度：' + res.longitude);
			// 	that.latitude = res.latitude;
			// 	that.longitude = res.longitude;
			// });
			
			// // 获取当前用户坐标--无用
			// uni.getLocation({
			// 	type: 'gcj02',
			// 	altitude:'true',
			// 	isHighAccuracy:"true",
			// 	accuracy:"best",
			// 	success: function(res) {
			// 		console.log("经度：" + res.longitude);
			// 		console.log("纬度：" + res.latitude);
			// 		that.latitude = res.latitude;
			// 		that.longitude = res.longitude;
			// 			uni.showModal({
			// 				title:"经度：" + res.longitude+"纬度：" + res.latitude,
			// 			})
			// 		},false:function(res){
			// 			uni.showModal({
			// 				title:"false"
			// 			})
			// 		}
			// });
			
			
			this.check();
			
			// 获取数据库中的所有景点标记
			// uni.request({
			// 	url:base.host+'markers/getAll',
			// 	success(res) {
			// 		that.markers=res.data.data;
			// 		console.log(that.markers); 
			// 	}
			// });
			// uni.request({
			// 	url:base.host+'scene/getAll',
			// 	success(res){
			// 		that.scenes=res.data.data;
			// 		console.log(that.scenes);
			// 	}
			// });
			
			// 创建音频实例
			this.innerAudioContext = wx.createInnerAudioContext({
				useWebAudioImplement: false // 是否使用 WebAudio 作为底层音频驱动，默认关闭。对于短音频、播放频繁的音频建议开启此选项，开启后将获得更优的性能表现。由于开启此选项后也会带来一定的内存增长，因此对于长音频建议关闭此选项
			})
			console.log("audio======",this.innerAudioContext);
		
			
		},
		methods: {
			//测试
			update:function(){
				let that=this;
					
				uni.getLocation({
					type: 'gcj02',
					altitude:'true',
					isHighAccuracy:"true",
					accuracy:"best",
					success: function(res) {
						console.log("经度：" + res.longitude);
						console.log("纬度：" + res.latitude);
						that.latitude = res.latitude;
						that.longitude = res.longitude;
							// uni.showModal({
							// 	title:"经度：" + res.longitude+"纬度：" + res.latitude,
							// })
						},false:function(res){
							uni.showModal({
								title:"false"
							})
						}
				});
			},
			
			//将数据下载，若缓存中有，从缓存中取
			check :function (){
				let that=this;
				// 获取本地缓存中的数据
				that.markers = wx.getStorageSync('markers');
				that.scenes = wx.getStorageSync('scenes');
				console.log(that.markers);
				const hash = require('hash.js');
				
				//计算哈希值函数
				function calculateHash(data) {
				  // 使用 SHA-256 算法计算哈希值
				    const sha256 = hash.sha256();
				    sha256.update(JSON.stringify(data));
					console.log(sha256.digest('hex'));
				    return sha256.digest('hex');
				}
				
				console.log("进入缓存:markers:"+wx.getStorageSync('markers'))
				// 发送请求获取服务器最新数据
				wx.request({
				  url: base.host+'markers/getAll',
				  success: function (res) {
				    const serverData = res.data.data;
				
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
			
			
			// 计算两个位置的坐标,输入两个位置的经纬度,单位为米
			getDistance: function(lat1, lng1, lat2, lng2) {
			  lat1 = lat1 || 0;
			  lng1 = lng1 || 0;
			  lat2 = lat2 || 0;
			  lng2 = lng2 || 0;
			
			  var rad1 = lat1 * Math.PI / 180.0;
			  var rad2 = lat2 * Math.PI / 180.0;
			  var a = rad1 - rad2;
			  var b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;
			  var r = 6378137;
			  var distance = r * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(rad1) * Math.cos(rad2) * Math.pow(Math.sin(b / 2), 2)));
			  
			  return distance;
			},
			
			// //计算各个标记点与用户的距离
			calculateDistances:function () {
				console.log("进入计算距离函数");
				for(var i=0;i<this.markers.length;i++){
					var marker=this.markers[i];
					var distance=this.getDistance(this.latitude,this.longitude,marker.latitude,marker.longitude);
					console.log("distance："+distance);
					if(distance<this.scenes[i].radius){
						if(this.isPlayed===0||this.isPlayed!==i){
							this.innerAudioContext.src=this.scenes[i].audio;
							this.innerAudioContext.play();
							this.isPlayed=i;
							console.log("scene:",this.scenes[i].radius);
							console.log("audio:",this.audio);
							break;
						}
						

					}
				}
			},
			
			stopRadio :function(){
				console.log("audio======",this.innerAudioContext);
				this.innerAudioContext.pause();
			},
			
			
			// 测试--两点距离
			// getlocation:function(){
			// 	uni.showModal({
			// 		title:"距离为"+this.getDistance(this.markers[0].latitude,this.markers[0].longitude,this.markers[1].latitude,this.markers[1].longitude),
			// 	})
			// },
			
		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}
	#map1{
		width:100%;
		height: calc(100vh);	
	}
	.floating-button {
	  position: absolute;
	  bottom: 20px; /* 距离底部的距离 */
	  right: 20px; /* 距离右侧的距离 */
	  width: 50px;
	  height: 50px;
	  background-color: #007bff;
	  color: white;
	  border-radius: 50%; /* 将按钮设置为圆形 */
	  display: flex;
	  justify-content: center;
	  align-items: center;
	  font-size: 20px;
	}

	
</style>
