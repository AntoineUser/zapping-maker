package fr.agauthier.zappingmaker;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ZappingMakerApplicationIT.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class ZappingMakerApplicationIT {

	@Test
	void ffmpeg_ok()throws java.io.IOException  {
		String filesPath = ClassLoader.getSystemResource("__files/").toString();

		FFmpeg ffmpeg = new FFmpeg("/usr/local/opt/ffmpeg/bin/ffmpeg");
		FFprobe ffprobe = new FFprobe("/usr/local/opt/ffmpeg/bin/ffprobe");

		FFmpegProbeResult probeResult = ffprobe.probe(filesPath + "test1.mp4");

		FFmpegBuilder builder = new FFmpegBuilder()

				.setInput(probeResult)     // Filename, or a FFmpegProbeResult
				.overrideOutputFiles(true) // Override the output if it exists

				.addOutput(filesPath + "output.mp4")   // Filename for the destination
				.setFormat("mp4")        // Format is inferred from filename, or can be set
				.setTargetSize(250000)  // Aim for a 250KB file

				.disableSubtitle()       // No subtiles

				.setAudioChannels(1)         // Mono audio
				.setAudioCodec("aac")        // using the aac codec
				.setAudioSampleRate(48_000)  // at 48KHz
				.setAudioBitRate(32768)      // at 32 kbit/s

				.setVideoCodec("libx264")     // Video using x264
				.setVideoFrameRate(24, 1)     // at 24 frames per second
				.setVideoResolution(640, 480) // at 640x480 resolution

				.setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
				.done();

		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

		// Run a one-pass encode
		executor.createJob(builder).run();

		// Or run a two-pass encode (which is better quality at the cost of being slower)
		executor.createTwoPassJob(builder).run();
	}

}
