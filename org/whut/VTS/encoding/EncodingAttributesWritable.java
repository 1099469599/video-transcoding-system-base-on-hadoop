package org.whut.VTS.encoding;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class EncodingAttributesWritable implements Writable {

	//��Ƶ��HDFS�ϵ�λ��
	private String splitDirOnHDFS;
	
	//ʹ�õ���Ƶ����
	private String audioCodec;
	
	//��Ƶ������
	private Integer audioBitRate;
	
	//��Ƶ��������
	private Integer audioChannels;
	
	//��Ƶ����Ƶ��
	private Integer audioSamplingRate;
	
	//��Ƶ���뷽ʽ
	private String videoCodec;
	
	//��Ƶ������
	private Integer videoBitRate;
	
	//��Ƶ֡��
	private Integer videoFrameRate;
	
	//��Ƶ��
	private Integer videoLength;
	
	//��Ƶ��
	private Integer videoWidth;
	
	
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		splitDirOnHDFS=arg0.readUTF();
		audioCodec=arg0.readUTF();
		
		audioBitRate=arg0.readInt();
		audioChannels=arg0.readInt();
		audioSamplingRate=arg0.readInt();
		
		videoCodec=arg0.readUTF();
		videoBitRate=arg0.readInt();
		videoFrameRate=arg0.readInt();
		videoLength=arg0.readInt();
		videoWidth=arg0.readInt();
	}

	
	
	public EncodingAttributesWritable(String splitDirOnHDFS, String audioCodec,
			Integer audioBitRate, Integer audioChannels,
			Integer audioSamplingRate, String videoCodec, Integer videoBitRate,
			Integer videoFrameRate, Integer videoLength, Integer videoWidth) {
		super();
		this.splitDirOnHDFS = splitDirOnHDFS;
		this.audioCodec = audioCodec;
		this.audioBitRate = audioBitRate;
		this.audioChannels = audioChannels;
		this.audioSamplingRate = audioSamplingRate;
		this.videoCodec = videoCodec;
		this.videoBitRate = videoBitRate;
		this.videoFrameRate = videoFrameRate;
		this.videoLength = videoLength;
		this.videoWidth = videoWidth;
	}

	

	public EncodingAttributesWritable() {
		super();
	}



	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(splitDirOnHDFS);
		arg0.writeUTF(audioCodec);
		
		arg0.writeInt(audioBitRate);
		arg0.writeInt(audioChannels);
		arg0.writeInt(audioSamplingRate);
		
		arg0.writeUTF(videoCodec);
		arg0.writeInt(videoBitRate);
		arg0.writeInt(videoFrameRate);
		arg0.writeInt(videoLength);
		arg0.writeInt(videoWidth);
	}



	public String getSplitDirOnHDFS() {
		return splitDirOnHDFS;
	}



	public void setSplitDirOnHDFS(String splitDirOnHDFS) {
		this.splitDirOnHDFS = splitDirOnHDFS;
	}



	public String getAudioCodec() {
		return audioCodec;
	}



	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}



	public Integer getAudioBitRate() {
		return audioBitRate;
	}



	public void setAudioBitRate(Integer audioBitRate) {
		this.audioBitRate = audioBitRate;
	}



	public Integer getAudioChannels() {
		return audioChannels;
	}



	public void setAudioChannels(Integer audioChannels) {
		this.audioChannels = audioChannels;
	}



	public Integer getAudioSamplingRate() {
		return audioSamplingRate;
	}



	public void setAudioSamplingRate(Integer audioSamplingRate) {
		this.audioSamplingRate = audioSamplingRate;
	}



	public String getVideoCodec() {
		return videoCodec;
	}



	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}



	public Integer getVideoBitRate() {
		return videoBitRate;
	}



	public void setVideoBitRate(Integer videoBitRate) {
		this.videoBitRate = videoBitRate;
	}



	public Integer getVideoFrameRate() {
		return videoFrameRate;
	}



	public void setVideoFrameRate(Integer videoFrameRate) {
		this.videoFrameRate = videoFrameRate;
	}



	public Integer getVideoLength() {
		return videoLength;
	}



	public void setVideoLength(Integer videoLength) {
		this.videoLength = videoLength;
	}



	public Integer getVideoWidth() {
		return videoWidth;
	}



	public void setVideoWidth(Integer videoWidth) {
		this.videoWidth = videoWidth;
	}

}
