package org.whut.VTS.encoding;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.FFMPEGLocator;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.VideoAttributes;
import it.sauronsoftware.jave.VideoSize;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.whut.VTS.utils.VTSUtil;

public class EncodingUsingJAVEMapper extends Mapper<Text, FileSplit, Text,Text>{
	@Override
	protected void map(Text key, FileSplit value,Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		//ͳ�Ʒ�Ƭ����
		context.getCounter("hadoop","splitNum").increment(1);
		
		//��ȡת����Ϣ
		
		//ʹ�õ���Ƶ����
		String audioCodec=context.getConfiguration().get("audioCodec", "libmp3lame");
		
		//��Ƶ������
		Integer audioBitRate=context.getConfiguration().getInt("audioBitRate", 64000);
		
		//��Ƶ��������
		Integer audioChannels=context.getConfiguration().getInt("audioChannels", 1);
		//��Ƶ����Ƶ��
		Integer audioSamplingRate=context.getConfiguration().getInt("audioSamplingRate",22050);
		//��Ƶ���뷽ʽ
		String videoCodec=context.getConfiguration().get("videoCodec","flv");
		//��Ƶ������
		Integer videoBitRate=context.getConfiguration().getInt("videoBitRate", 160000);
		//��Ƶ֡��
		Integer videoFrameRate=context.getConfiguration().getInt("videoFrameRate", 15);
		//��Ƶ��
		Integer videoLength=context.getConfiguration().getInt("videoLength", 400);
		//��Ƶ��
		Integer videoWidth=context.getConfiguration().getInt("videoWidth", 300);
		
		//��ȡ��ת���ļ�����
		String videoName=value.getPath().getName();
		
		//��ȡ��ת���ļ�Ŀ¼����
		String videoDirOnHDFS=value.getPath().getParent().getName();
		
		
		//���������ļ���
		String LocalDownloadDir="/"+context.getConfiguration().get("localdownloadDir","tmp")+"/"+videoDirOnHDFS;
		
		//��HDFS�����ش�ת���ļ�������
		FileSystem hdfs=FileSystem.get(context.getConfiguration());
		FileSystem local=FileSystem.getLocal(context.getConfiguration());
		
		FSDataInputStream in=hdfs.open(value.getPath());
		
		local.createNewFile(new Path(LocalDownloadDir+"/"+videoName));
		IOUtils.copyBytes(in,local.create(new Path(LocalDownloadDir+"/"+videoName)), 2048,true);
		
		File source=new File(LocalDownloadDir+"/"+videoName);
		File target=new File(LocalDownloadDir+"/"+VTSUtil.getFileNameNoEx(videoName)+"."+videoCodec);
		
		AudioAttributes audio=new AudioAttributes();
		audio.setCodec(audioCodec);
		audio.setBitRate(new Integer(audioBitRate));
		audio.setChannels(new Integer(audioChannels));
		audio.setSamplingRate(new Integer(audioSamplingRate));
		
		VideoAttributes video=new VideoAttributes();
		video.setCodec(videoCodec);
		video.setBitRate(videoBitRate);
		video.setFrameRate(videoFrameRate);
		video.setSize(new VideoSize(videoWidth, videoLength));
		
		EncodingAttributes attrs=new EncodingAttributes();
		attrs.setFormat(videoCodec);
		attrs.setAudioAttributes(audio);
		attrs.setVideoAttributes(video);
		Encoder encoder=new Encoder(new FFMPEGLocator() {
			
			@Override
			protected String getFFMPEGExecutablePath() {
				// TODO Auto-generated method stub
				return "ffmpeg";
			}
		});
		
		try {
			encoder.encode(source, target, attrs);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InputFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//�ϴ�ת������Ƶ
		IOUtils.copyBytes(local.open(new Path(LocalDownloadDir+"/"+VTSUtil.getFileNameNoEx(videoName)+"."+videoCodec)),hdfs.create(new Path(videoDirOnHDFS+"/"+VTSUtil.getFileNameNoEx(videoName)+"."+videoCodec)), 2048,true);
		
		context.write(key, new Text(videoDirOnHDFS+"/"+VTSUtil.getFileNameNoEx(videoName)+"."+videoCodec));
	}	
}
