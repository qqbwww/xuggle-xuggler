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

package com.xuggle.mediatool;



import com.xuggle.xuggler.IContainer;

/**
 * 所有{@link IMediaCoder}方法的一个抽象实现，仅仅是没有声明{@link IMediaCoder}
 * <p>
 * 
 * Mixin classes can be extended by anyone, but the extending class
 * gets to decide which, if any, of the interfaces they actually
 * want to support.
 * 
 * </p>
 * 
 * @author trebor
 * @author aclarke
 */

public abstract class AMediaCoderMixin extends AMediaToolMixin
{
  //读写用到的容器
  private final IContainer mContainer;

  //媒体写应该关闭容器则置为true
  private boolean mCloseContainer;

  //要读取或写入的URL
  private final String mUrl;

  // all the media reader listeners

  /**
   * 构造一个{@link AMediaCoderMixin}.
   *
   * @param url the URL which will be read or written to
   * @param container the container which be read from or written to
   */
  
  public AMediaCoderMixin(String url, IContainer container)
  {
    mUrl = url;
    mContainer = container.copyReference();

    // 假设容器不应该被工具关闭，之后调用open()可能会修改这些行为
    setShouldCloseContainer(false);
  }

  /**
   * The URL from which the {@link IContainer} is being read or written to.
   * 
   * @return the source or destination URL.
   */

  public String getUrl()
  {
    return mUrl;
  }

  /** 
   * Get the underlying media {@link IContainer} that the {@link IMediaCoder} is
   * reading from or writing to.  The returned {@link IContainer} can
   * be further interrogated for media stream details.
   *
   * @return the media container.
   */

  public IContainer getContainer()
  {
    return mContainer == null ? null : mContainer.copyReference();
  }

  /**
   * 测试{@link IMediaCoder}是否已经打开
   * 
   * @return true if the media tool is open.
   */

  public boolean isOpen()
  {
    return mContainer.isOpened();
  }

  /**
   * 设置{@link IMediaCoder}行为，当调用{@link IMediaCoder#close()}方法
   * 时是否要调用{@link IContainer#close()}
   * @param value should we close the container
   */
  public void setShouldCloseContainer(boolean value)
  {
    mCloseContainer = value;
  }

  /**
   * Should this {@link IMediaCoder} call {@link IContainer#close()}
   * when {@link IMediaCoder#close()} is called.
   * 
   * @return should we close the container
   */
  public boolean getShouldCloseContainer()
  {
    return mCloseContainer;
  }
}
