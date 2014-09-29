/*******************************************************************************
 * Copyright (c) 2008, 2010 Xuggle Inc.  All rights reserved.
 *  
 * This file is part of Xuggle-Xuggler-Main.
 *
 * Xuggle-Xuggler-Main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Xuggle-Xuggler-Main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Xuggle-Xuggler-Main.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

/**
 * <p>
 * 一个简单的API用于解码，观看和编码一个媒体:
 * <a href="http://blog.xuggle.com/2009/06/05/introduction-to-xuggler-mediatools/">tutorial here</a>; start with
 * {@link com.xuggle.mediatool.ToolFactory}.
 * </p>
 * 
 * <h2>Examples</h2>
 * 
 * <p>
 * 下面代码解码一个FLV文件，并编码到Quicktime文件。
 * </p>
 * 
 * <pre>
 * IMediaReader reader = ToolFactory.makeReader(&quot;input.flv&quot;);
 * reader.addListener(ToolFactory.makeWriter(&quot;output.mov&quot;, reader));
 * while (reader.readPacket() == null)
 *   ;
 * </pre>
 * 
 * <p>
 * For more examples of using the mediatools see the
 * com.xuggle.mediatool.demos demonstration package.
 * </p>
 * <h2>Tutorials</h2>
 * <h3>Using MediaTool to Decode &amp; Encode</h3>
 * <div style="text-align: center">
<object width="425" height="344"><param name="movie" value="http://www.youtube.com/v/9aJvPPzhyik&fmt=18&color1=0xb1b1b1&color2=0xcfcfcf&feature=player_embedded&fs=1"></param><param name="allowFullScreen" value="true"></param><embed src="http://www.youtube.com/v/9aJvPPzhyik&fmt=18&color1=0xb1b1b1&color2=0xcfcfcf&feature=player_embedded&fs=1" type="application/x-shockwave-flash" allowfullscreen="true" width="425" height="344"></embed></object>
    </div>
   <h3>Using MediaTool to Change &amp; Create Media</h3>
   <div style="text-align: center">
<object width="425" height="344"><param name="movie" value="http://www.youtube.com/v/JBXyE4_3ERI&fmt=18&color1=0xb1b1b1&color2=0xcfcfcf&feature=player_embedded&fs=1"></param><param name="allowFullScreen" value="true"></param><embed src="http://www.youtube.com/v/JBXyE4_3ERI&fmt=18&color1=0xb1b1b1&color2=0xcfcfcf&feature=player_embedded&fs=1" type="application/x-shockwave-flash" allowfullscreen="true" width="425" height="344"></embed></object>
   </div>
   <h3>Text Version of Tutorials</h3>
 * <p>
 * Check out the <a
 * href="http://wiki.xuggle.com/MediaTool_Introduction">MediaTool Tutorial</a>
 * on our Wiki site.
 * </p>
 * 
 * <h2>How To Use</h2>
 * <p>
 * 
 * To create {@link com.xuggle.mediatool.IMediaReader},
 * {@link com.xuggle.mediatool.IMediaWriter},
 * {@link com.xuggle.mediatool.IMediaViewer} or
 * {@link com.xuggle.mediatool.IMediaDebugListener} objects, see the
 * {@link com.xuggle.mediatool.ToolFactory} class.
 * 
 * </p>
 * <p>
 * 
 * The {@link com.xuggle.mediatool.IMediaReader} and
 * {@link com.xuggle.mediatool.IMediaWriter} objects are the workhorses of this
 * package. They read and write to {@link com.xuggle.xuggler.IContainer}
 * objects, but hide the the complexity of encoding and decoding audio. Instead,
 * they generate events that they notify intertested
 * {@link com.xuggle.mediatool.IMediaListener} objects about. Interested
 * {@link com.xuggle.mediatool.IMediaListener} objects are registered through
 * the {@link com.xuggle.mediatool.IMediaGenerator} interface, which both
 * {@link com.xuggle.mediatool.IMediaReader} and
 * {@link com.xuggle.mediatool.IMediaWriter} extend.
 * 
 * </p>
 * <p>
 * 
 * {@link com.xuggle.mediatool.IMediaCoder} objects (which both
 * {@link com.xuggle.mediatool.IMediaReader} and
 * {@link com.xuggle.mediatool.IMediaWriter} are) will make intelligent guesses
 * about the parameters to decode and encode with based on the URLs or file
 * names you create the objects with, but you can change and override everything
 * if you want. To do that use the
 * {@link com.xuggle.mediatool.IMediaCoder#getContainer()} interface to get the
 * underlying {@link com.xuggle.xuggler.IContainer} object where they can then
 * query all other information. If your code is executing inside a
 * {@link com.xuggle.mediatool.IMediaListener} method, you can get the object
 * that generated that event by calling
 * {@link com.xuggle.mediatool.event.IEvent#getSource()} of an
 * {@link com.xuggle.mediatool.IMediaListener} event, and from there you can
 * query the {@link com.xuggle.xuggler.IContainer} if needed.
 * 
 * </p>
 * <p>
 * 
 * An {@link com.xuggle.mediatool.IMediaViewer} object is an
 * <strong>experimental</strong> interface that can be added to a
 * {@link com.xuggle.mediatool.IMediaGenerator} to display audio and video data
 * that the {@link com.xuggle.mediatool.IMediaReader} is generating in real
 * time. <strong>This Tool is currently alpha and pretty buggy, but can
 * be helpful for debugging video</strong>.
 * 
 * </p>
 * <p>
 * 
 * An {@link com.xuggle.mediatool.IMediaDebugListener} object can be
 * attached to {@link com.xuggle.mediatool.IMediaGenerator} objects and will log
 * the events they generate to a log file. See the <a
 * href="http://logback.qos.ch/">logback</a> logging project for information on
 * how to logback.
 * 
 * </p>
 * <p>
 * 
 * Lastly if you want to provide your own implementations of any of the
 * interfaces in this package, a series of Adaptors and Mixin classes are
 * provided.
 * 
 * </p>
 * <p>
 * 
 * Adapter classes are used when you want to implement one of the interfaces,
 * but want a lot of the implementation provided for you. For example,
 * {@link com.xuggle.mediatool.MediaListenerAdapter} provides an implementation
 * of {@link com.xuggle.mediatool.IMediaListener} with all methods implemented
 * as empty (no-op) methods. This means you can create your own
 * {@link com.xuggle.mediatool.IMediaListener} objects that only override some
 * methods.
 * 
 * </p>
 * <p>
 * 
 * Mixin类类似于Adapter类,但是没有声明它们要实现的接口。通过这种方式，这样它们可以被包含在
 * 子类，却不需要强制子类声明它们实现的方法，举例来说， {@link com.xuggle.mediatool.AMediaToolMixin}
 * 类可辅助实现 {@link com.xuggle.mediatool.IMediaReader}（事实上，我们在内部确实是
 * 这样做的)。
 * 
 * </p>
 * 
 * <h2>如何构建一个Meida管道</h2>
 * 
 * <p>
 * 有时候链接一系列对象过滤媒体并提供影响很有用，查看那{@link com.xuggle.mediatool.demos.ModifyAudioAndVideo}
 * 示例，下面是构造管道的代码示例
 * </p>
 * 
 * <pre>
 * IMediaReader reader = ToolFactory.makeReader(inputFile.toString());
 * reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
 * 
 * // create a writer and configure it's parameters from the reader
 * 
 * IMediaWriter writer = ToolFactory.makeWriter(outputFile.toString(), reader);
 * 
 * // 创建一个工具，画时间戳到视频
 * 
 * IMediaTool addTimeStamp = new TimeStampTool();
 * 
 * // 创建一个工具，将音量降到十分之一
 * 
 * IMediaTool reduceVolume = new VolumeAdjustTool(0.1);
 * 
 * // create a tool chain:
 * //   reader -&gt; addTimeStamp -&gt; reduceVolume -&gt; writer
 * 
 * reader.addListener(addTimeStamp);
 * addTimeStamp.addListener(reduceVolume);
 * reduceVolume.addListener(writer);
 * 
 * // add a viewer to the writer, to see the modified media
 * 
 * writer.addListener(ToolFactory.makeViewer(AUDIO_VIDEO));
 * 
 * // read and decode packets from the source file and
 * // then encode and write out data to the output file
 * 
 * while (reader.readPacket() == null)
 *   ;
 * </pre>
 * 
 * <h2>包使用规则</h2>
 * 
 * <p>
 *
 * 当使用这个包时，你应该知道这些代码规范
 *
 * </p>
 * <ul>
 * <li>All interfaces begin with the letter &quot;I&quot;. For example:
 * {@link com.xuggle.mediatool.IMediaListener}.</li>
 * <li>All abstract classes begin with the latter &quot;A&quot;. For example:
 * {@link com.xuggle.mediatool.AMediaListenerMixin}.</li>
 * <li>Event interfaces and classes can be found in
 * com.xuggle.mediatool.event and end with &quot;Event;&quot;. For
 * example: {@link com.xuggle.mediatool.event.AddStreamEvent}.</li>
 * <li>Mixin classes will implement all methods suggested by their name, but
 * will not declare the corresponding interface. For example:
 * {@link com.xuggle.mediatool.AMediaListenerMixin}.</li>
 * <li>Adapter classes will provide an implementation of all methods suggested
 * by their name, and also will declare the corresponding interface. For
 * example: {@link com.xuggle.mediatool.MediaListenerAdapter}.</li>
 * </ul>
 */

package com.xuggle.mediatool;
