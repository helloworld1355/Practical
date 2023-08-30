<template>
	<view class="content">
		<h1 >{{marker.title}}</h1>
		<image :src="marker.iconPath"></image>
		<rich-text :nodes="scene.content" style="margin-top: 10px;"></rich-text>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				scene:'',
				marker:'',
			}
		},
		onLoad:function(option){
			const index=option.index;
			const scenes=wx.getStorageSync("scenes");
			this.scene=scenes[index];	
			this.scene.content=this.scene.content;
			console.log("scene:",scenes[index]);
			const markers=wx.getStorageSync("markers");
			this.marker=markers[index];
			console.log("marker:",markers[index]);
		},
		methods: {
			
		}
	}
</script>

<style>
.content{
	width: 100%;
	height: calc(100);
	align-items: center;
}
h1{
	margin: 10px 0;
	text-align: center;
}

</style>
