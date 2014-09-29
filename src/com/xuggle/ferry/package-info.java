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
 * 一堆用于Java和原生代码ferry（转换？这货要怎么翻译啊），管理本地内存。
 * <p>
 * 这个包含有一些供Xuggler用于滚里Java和原生代码通信的类。
 * </p>
 * <p>
 * 标记为Internal Only的类方法不要在这个库之外使用。它们是public的原因是为了供其他Xuggler包使用，
 * 不要在Xuggler之外调用它们，那样很容易让JVM宕机。
 * </p>
 * <h2>Tuning Ferry (And Xuggler) Memory Management</h2>
 * 
 * <p>
 * A complicated subject, but if you're ready for it, take a look at the
 * {@link com.xuggle.ferry.JNIMemoryManager} object and start reading.
 * </p>
 */
package com.xuggle.ferry;

