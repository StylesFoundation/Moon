package org.lwjgl.system.linux.liburing;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.linux.EpollEvent;
import org.lwjgl.system.linux.IOVec;
import org.lwjgl.system.linux.KernelTimespec;
import org.lwjgl.system.linux.Msghdr;
import org.lwjgl.system.linux.OpenHow;
import org.lwjgl.system.linux.Sockaddr;
import org.lwjgl.system.linux.Statx;

public class LibURing {
   public static final long LIBURING_UDATA_TIMEOUT = -1L;

   protected LibURing() {
      throw new UnsupportedOperationException();
   }

   public static native long nio_uring_get_probe_ring(long var0);

   @Nullable
   @NativeType("struct io_uring_probe *")
   public static IOURingProbe io_uring_get_probe_ring(@NativeType("struct io_uring *") IOURing ring) {
      long __result = nio_uring_get_probe_ring(ring.address());
      return IOURingProbe.createSafe(__result);
   }

   public static native long nio_uring_get_probe();

   @Nullable
   @NativeType("struct io_uring_probe *")
   public static IOURingProbe io_uring_get_probe() {
      long __result = nio_uring_get_probe();
      return IOURingProbe.createSafe(__result);
   }

   public static native void nio_uring_free_probe(long var0);

   public static void io_uring_free_probe(@NativeType("struct io_uring_probe *") IOURingProbe probe) {
      nio_uring_free_probe(probe.address());
   }

   public static native int nio_uring_opcode_supported(long var0, int var2);

   public static int io_uring_opcode_supported(@NativeType("struct io_uring_probe const *") IOURingProbe p, int op) {
      return nio_uring_opcode_supported(p.address(), op);
   }

   public static native int nio_uring_queue_init_params(int var0, long var1, long var3);

   public static int io_uring_queue_init_params(
      @NativeType("unsigned") int entries, @NativeType("struct io_uring *") IOURing ring, @NativeType("struct io_uring_params *") IOURingParams p
   ) {
      return nio_uring_queue_init_params(entries, ring.address(), p.address());
   }

   public static native int nio_uring_queue_init(int var0, long var1, int var3);

   public static int io_uring_queue_init(@NativeType("unsigned") int entries, @NativeType("struct io_uring *") IOURing ring, @NativeType("unsigned") int flags) {
      return nio_uring_queue_init(entries, ring.address(), flags);
   }

   public static native int nio_uring_queue_mmap(int var0, long var1, long var3);

   public static int io_uring_queue_mmap(int fd, @NativeType("struct io_uring_params *") IOURingParams p, @NativeType("struct io_uring *") IOURing ring) {
      return nio_uring_queue_mmap(fd, p.address(), ring.address());
   }

   public static native int nio_uring_ring_dontfork(long var0);

   public static int io_uring_ring_dontfork(@NativeType("struct io_uring *") IOURing ring) {
      return nio_uring_ring_dontfork(ring.address());
   }

   public static native void nio_uring_queue_exit(long var0);

   public static void io_uring_queue_exit(@NativeType("struct io_uring *") IOURing ring) {
      nio_uring_queue_exit(ring.address());
   }

   public static native int nio_uring_peek_batch_cqe(long var0, long var2, int var4);

   @NativeType("unsigned")
   public static int io_uring_peek_batch_cqe(@NativeType("struct io_uring *") IOURing ring, @NativeType("struct io_uring_cqe **") PointerBuffer cqes) {
      return nio_uring_peek_batch_cqe(ring.address(), MemoryUtil.memAddress(cqes), cqes.remaining());
   }

   public static native int nio_uring_wait_cqes(long var0, long var2, int var4, long var5, long var7);

   public static int io_uring_wait_cqes(
      @NativeType("struct io_uring *") IOURing ring,
      @NativeType("struct io_uring_cqe **") PointerBuffer cqe_ptr,
      @Nullable @NativeType("struct __kernel_timespec *") KernelTimespec ts,
      @NativeType("sigset_t *") long sigmask
   ) {
      return nio_uring_wait_cqes(ring.address(), MemoryUtil.memAddress(cqe_ptr), cqe_ptr.remaining(), MemoryUtil.memAddressSafe(ts), sigmask);
   }

   public static native int nio_uring_wait_cqe_timeout(long var0, long var2, long var4);

   public static int io_uring_wait_cqe_timeout(
      @NativeType("struct io_uring *") IOURing ring,
      @NativeType("struct io_uring_cqe **") PointerBuffer cqe_ptr,
      @Nullable @NativeType("struct __kernel_timespec *") KernelTimespec ts
   ) {
      if (Checks.CHECKS) {
         Checks.check(cqe_ptr, 1);
      }

      return nio_uring_wait_cqe_timeout(ring.address(), MemoryUtil.memAddress(cqe_ptr), MemoryUtil.memAddressSafe(ts));
   }

   public static native int nio_uring_submit(long var0);

   public static int io_uring_submit(@NativeType("struct io_uring *") IOURing ring) {
      return nio_uring_submit(ring.address());
   }

   public static native int nio_uring_submit_and_wait(long var0, int var2);

   public static int io_uring_submit_and_wait(@NativeType("struct io_uring *") IOURing ring, @NativeType("unsigned") int wait_nr) {
      return nio_uring_submit_and_wait(ring.address(), wait_nr);
   }

   public static native int nio_uring_submit_and_wait_timeout(long var0, long var2, int var4, long var5, long var7);

   public static int io_uring_submit_and_wait_timeout(
      @NativeType("struct io_uring *") IOURing ring,
      @NativeType("struct io_uring_cqe **") PointerBuffer cqe_ptr,
      @Nullable @NativeType("struct __kernel_timespec *") KernelTimespec ts,
      @NativeType("sigset_t *") long sigmask
   ) {
      return nio_uring_submit_and_wait_timeout(ring.address(), MemoryUtil.memAddress(cqe_ptr), cqe_ptr.remaining(), MemoryUtil.memAddressSafe(ts), sigmask);
   }

   public static native long nio_uring_get_sqe(long var0);

   @Nullable
   @NativeType("struct io_uring_sqe *")
   public static IOURingSQE io_uring_get_sqe(@NativeType("struct io_uring *") IOURing ring) {
      long __result = nio_uring_get_sqe(ring.address());
      return IOURingSQE.createSafe(__result);
   }

   public static native int nio_uring_register_buffers(long var0, long var2, int var4);

   public static int io_uring_register_buffers(@NativeType("struct io_uring *") IOURing ring, @NativeType("struct iovec const *") IOVec.Buffer iovecs) {
      return nio_uring_register_buffers(ring.address(), iovecs.address(), iovecs.remaining());
   }

   public static native int nio_uring_register_buffers_tags(long var0, long var2, long var4, int var6);

   public static int io_uring_register_buffers_tags(
      @NativeType("struct io_uring *") IOURing ring, @NativeType("struct iovec const *") IOVec.Buffer iovecs, @NativeType("__u64 const *") LongBuffer tags
   ) {
      if (Checks.CHECKS) {
         Checks.check(tags, iovecs.remaining());
      }

      return nio_uring_register_buffers_tags(ring.address(), iovecs.address(), MemoryUtil.memAddress(tags), iovecs.remaining());
   }

   public static native int nio_uring_register_buffers_update_tag(long var0, int var2, long var3, long var5, int var7);

   public static int io_uring_register_buffers_update_tag(
      @NativeType("struct io_uring *") IOURing ring,
      @NativeType("unsigned") int off,
      @NativeType("struct iovec const *") IOVec.Buffer iovecs,
      @NativeType("__u64 const *") LongBuffer tags
   ) {
      if (Checks.CHECKS) {
         Checks.check(tags, iovecs.remaining());
      }

      return nio_uring_register_buffers_update_tag(ring.address(), off, iovecs.address(), MemoryUtil.memAddress(tags), iovecs.remaining());
   }

   public static native int nio_uring_unregister_buffers(long var0);

   public static int io_uring_unregister_buffers(@NativeType("struct io_uring *") IOURing ring) {
      return nio_uring_unregister_buffers(ring.address());
   }

   public static native int nio_uring_register_files(long var0, long var2, int var4);

   public static int io_uring_register_files(@NativeType("struct io_uring *") IOURing ring, @NativeType("int const *") IntBuffer files) {
      return nio_uring_register_files(ring.address(), MemoryUtil.memAddress(files), files.remaining());
   }

   public static native int nio_uring_register_files_tags(long var0, long var2, long var4, int var6);

   public static int io_uring_register_files_tags(
      @NativeType("struct io_uring *") IOURing ring, @NativeType("int const *") IntBuffer files, @NativeType("__u64 const *") LongBuffer tags
   ) {
      if (Checks.CHECKS) {
         Checks.check(tags, files.remaining());
      }

      return nio_uring_register_files_tags(ring.address(), MemoryUtil.memAddress(files), MemoryUtil.memAddress(tags), files.remaining());
   }

   public static native int nio_uring_register_files_update_tag(long var0, int var2, long var3, long var5, int var7);

   public static int io_uring_register_files_update_tag(
      @NativeType("struct io_uring *") IOURing ring,
      @NativeType("unsigned") int off,
      @NativeType("int const *") IntBuffer files,
      @NativeType("__u64 const *") LongBuffer tags
   ) {
      if (Checks.CHECKS) {
         Checks.check(tags, files.remaining());
      }

      return nio_uring_register_files_update_tag(ring.address(), off, MemoryUtil.memAddress(files), MemoryUtil.memAddress(tags), files.remaining());
   }

   public static native int nio_uring_unregister_files(long var0);

   public static int io_uring_unregister_files(@NativeType("struct io_uring *") IOURing ring) {
      return nio_uring_unregister_files(ring.address());
   }

   public static native int nio_uring_register_files_update(long var0, int var2, long var3, int var5);

   public static int io_uring_register_files_update(
      @NativeType("struct io_uring *") IOURing ring, @NativeType("unsigned") int off, @NativeType("int *") IntBuffer files
   ) {
      return nio_uring_register_files_update(ring.address(), off, MemoryUtil.memAddress(files), files.remaining());
   }

   public static native int nio_uring_register_eventfd(long var0, int var2);

   public static int io_uring_register_eventfd(@NativeType("struct io_uring *") IOURing ring, int fd) {
      return nio_uring_register_eventfd(ring.address(), fd);
   }

   public static native int nio_uring_register_eventfd_async(long var0, int var2);

   public static int io_uring_register_eventfd_async(@NativeType("struct io_uring *") IOURing ring, int fd) {
      return nio_uring_register_eventfd_async(ring.address(), fd);
   }

   public static native int nio_uring_unregister_eventfd(long var0);

   public static int io_uring_unregister_eventfd(@NativeType("struct io_uring *") IOURing ring) {
      return nio_uring_unregister_eventfd(ring.address());
   }

   public static native int nio_uring_register_probe(long var0, long var2, int var4);

   public static int io_uring_register_probe(
      @NativeType("struct io_uring *") IOURing ring, @NativeType("struct io_uring_probe *") IOURingProbe p, @NativeType("unsigned") int nr
   ) {
      return nio_uring_register_probe(ring.address(), p.address(), nr);
   }

   public static native int nio_uring_register_personality(long var0);

   public static int io_uring_register_personality(@NativeType("struct io_uring *") IOURing ring) {
      return nio_uring_register_personality(ring.address());
   }

   public static native int nio_uring_unregister_personality(long var0, int var2);

   public static int io_uring_unregister_personality(@NativeType("struct io_uring *") IOURing ring, int id) {
      return nio_uring_unregister_personality(ring.address(), id);
   }

   public static native int nio_uring_register_restrictions(long var0, long var2, int var4);

   public static int io_uring_register_restrictions(
      @NativeType("struct io_uring *") IOURing ring, @NativeType("struct io_uring_restriction *") IOURingRestriction.Buffer res
   ) {
      return nio_uring_register_restrictions(ring.address(), res.address(), res.remaining());
   }

   public static native int nio_uring_enable_rings(long var0);

   public static int io_uring_enable_rings(@NativeType("struct io_uring *") IOURing ring) {
      return nio_uring_enable_rings(ring.address());
   }

   public static native int n__io_uring_sqring_wait(long var0);

   public static int __io_uring_sqring_wait(@NativeType("struct io_uring *") IOURing ring) {
      return n__io_uring_sqring_wait(ring.address());
   }

   public static native int nio_uring_register_iowq_aff(long var0, long var2, long var4);

   public static int io_uring_register_iowq_aff(
      @NativeType("struct io_uring *") IOURing ring, @NativeType("size_t") long cpusz, @NativeType("cpu_set_t const *") long mask
   ) {
      if (Checks.CHECKS) {
         Checks.check(mask);
      }

      return nio_uring_register_iowq_aff(ring.address(), cpusz, mask);
   }

   public static native int nio_uring_unregister_iowq_aff(long var0);

   public static int io_uring_unregister_iowq_aff(@NativeType("struct io_uring *") IOURing ring) {
      return nio_uring_unregister_iowq_aff(ring.address());
   }

   public static native int nio_uring_register_iowq_max_workers(long var0, long var2);

   public static int io_uring_register_iowq_max_workers(@NativeType("struct io_uring *") IOURing ring, @NativeType("unsigned int *") IntBuffer values) {
      if (Checks.CHECKS) {
         Checks.check(values, 2);
      }

      return nio_uring_register_iowq_max_workers(ring.address(), MemoryUtil.memAddress(values));
   }

   public static native void nio_uring_cqe_seen(long var0, long var2);

   public static void io_uring_cqe_seen(@NativeType("struct io_uring *") IOURing ring, @NativeType("struct io_uring_cqe *") IOURingCQE cqe) {
      nio_uring_cqe_seen(ring.address(), cqe.address());
   }

   public static native void nio_uring_sqe_set_data(long var0, long var2);

   public static void io_uring_sqe_set_data(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("void *") long data) {
      if (Checks.CHECKS) {
         Checks.check(data);
      }

      nio_uring_sqe_set_data(sqe.address(), data);
   }

   public static native long nio_uring_cqe_get_data(long var0);

   @NativeType("void *")
   public static long io_uring_cqe_get_data(@NativeType("struct io_uring_cqe const *") IOURingCQE cqe) {
      return nio_uring_cqe_get_data(cqe.address());
   }

   public static native void nio_uring_sqe_set_data64(long var0, long var2);

   public static void io_uring_sqe_set_data64(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("__u64") long data) {
      nio_uring_sqe_set_data64(sqe.address(), data);
   }

   public static native long nio_uring_cqe_get_data64(long var0);

   @NativeType("__u64")
   public static long io_uring_cqe_get_data64(@NativeType("struct io_uring_cqe const *") IOURingCQE cqe) {
      return nio_uring_cqe_get_data64(cqe.address());
   }

   public static native void nio_uring_sqe_set_flags(long var0, int var2);

   public static void io_uring_sqe_set_flags(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("unsigned int") int flags) {
      nio_uring_sqe_set_flags(sqe.address(), flags);
   }

   public static native void nio_uring_prep_splice(long var0, int var2, long var3, int var5, long var6, int var8, int var9);

   public static void io_uring_prep_splice(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int fd_in,
      @NativeType("int64_t") long off_in,
      int fd_out,
      @NativeType("int64_t") long off_out,
      @NativeType("unsigned int") int nbytes,
      @NativeType("unsigned int") int splice_flags
   ) {
      nio_uring_prep_splice(sqe.address(), fd_in, off_in, fd_out, off_out, nbytes, splice_flags);
   }

   public static native void nio_uring_prep_tee(long var0, int var2, int var3, int var4, int var5);

   public static void io_uring_prep_tee(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int fd_in,
      int fd_out,
      @NativeType("unsigned int") int nbytes,
      @NativeType("unsigned int") int splice_flags
   ) {
      nio_uring_prep_tee(sqe.address(), fd_in, fd_out, nbytes, splice_flags);
   }

   public static native void nio_uring_prep_readv(long var0, int var2, long var3, int var5, int var6);

   public static void io_uring_prep_readv(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("struct iovec const *") IOVec.Buffer iovecs, int offset
   ) {
      nio_uring_prep_readv(sqe.address(), fd, iovecs.address(), iovecs.remaining(), offset);
   }

   public static native void nio_uring_prep_readv2(long var0, int var2, long var3, int var5, int var6, int var7);

   public static void io_uring_prep_readv2(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("struct iovec const *") IOVec.Buffer iovecs, int offset, int flags
   ) {
      nio_uring_prep_readv2(sqe.address(), fd, iovecs.address(), iovecs.remaining(), offset, flags);
   }

   public static native void nio_uring_prep_read_fixed(long var0, int var2, long var3, int var5, int var6, int var7);

   public static void io_uring_prep_read_fixed(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("void *") ByteBuffer buf, int offset, int buf_index
   ) {
      nio_uring_prep_read_fixed(sqe.address(), fd, MemoryUtil.memAddress(buf), buf.remaining(), offset, buf_index);
   }

   public static native void nio_uring_prep_writev(long var0, int var2, long var3, int var5, int var6);

   public static void io_uring_prep_writev(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("struct iovec const *") IOVec.Buffer iovecs, int offset
   ) {
      nio_uring_prep_writev(sqe.address(), fd, iovecs.address(), iovecs.remaining(), offset);
   }

   public static native void nio_uring_prep_writev2(long var0, int var2, long var3, int var5, int var6, int var7);

   public static void io_uring_prep_writev2(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("struct iovec const *") IOVec.Buffer iovecs, int offset, int flags
   ) {
      nio_uring_prep_writev2(sqe.address(), fd, iovecs.address(), iovecs.remaining(), offset, flags);
   }

   public static native void nio_uring_prep_write_fixed(long var0, int var2, long var3, int var5, int var6, int var7);

   public static void io_uring_prep_write_fixed(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("void const *") ByteBuffer buf, int offset, int buf_index
   ) {
      nio_uring_prep_write_fixed(sqe.address(), fd, MemoryUtil.memAddress(buf), buf.remaining(), offset, buf_index);
   }

   public static native void nio_uring_prep_recvmsg(long var0, int var2, long var3, int var5);

   public static void io_uring_prep_recvmsg(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("struct msghdr *") Msghdr msg, @NativeType("unsigned int") int flags
   ) {
      nio_uring_prep_recvmsg(sqe.address(), fd, msg.address(), flags);
   }

   public static native void nio_uring_prep_sendmsg(long var0, int var2, long var3, int var5);

   public static void io_uring_prep_sendmsg(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("struct msghdr const *") Msghdr msg, @NativeType("unsigned int") int flags
   ) {
      if (Checks.CHECKS) {
         Msghdr.validate(msg.address());
      }

      nio_uring_prep_sendmsg(sqe.address(), fd, msg.address(), flags);
   }

   public static native void nio_uring_prep_poll_add(long var0, int var2, int var3);

   public static void io_uring_prep_poll_add(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("unsigned int") int poll_mask) {
      nio_uring_prep_poll_add(sqe.address(), fd, poll_mask);
   }

   public static native void nio_uring_prep_poll_multishot(long var0, int var2, int var3);

   public static void io_uring_prep_poll_multishot(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("unsigned int") int poll_mask) {
      nio_uring_prep_poll_multishot(sqe.address(), fd, poll_mask);
   }

   public static native void nio_uring_prep_poll_remove(long var0, long var2);

   public static void io_uring_prep_poll_remove(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("__u64") long user_data) {
      nio_uring_prep_poll_remove(sqe.address(), user_data);
   }

   public static native void nio_uring_prep_poll_update(long var0, long var2, long var4, int var6, int var7);

   public static void io_uring_prep_poll_update(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      @NativeType("__u64") long old_user_data,
      @NativeType("__u64") long new_user_data,
      @NativeType("unsigned int") int poll_mask,
      @NativeType("unsigned int") int flags
   ) {
      nio_uring_prep_poll_update(sqe.address(), old_user_data, new_user_data, poll_mask, flags);
   }

   public static native void nio_uring_prep_fsync(long var0, int var2, int var3);

   public static void io_uring_prep_fsync(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("unsigned int") int fsync_flags) {
      nio_uring_prep_fsync(sqe.address(), fd, fsync_flags);
   }

   public static native void nio_uring_prep_nop(long var0);

   public static void io_uring_prep_nop(@NativeType("struct io_uring_sqe *") IOURingSQE sqe) {
      nio_uring_prep_nop(sqe.address());
   }

   public static native void nio_uring_prep_timeout(long var0, long var2, int var4, int var5);

   public static void io_uring_prep_timeout(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      @NativeType("struct __kernel_timespec *") KernelTimespec ts,
      @NativeType("unsigned int") int count,
      @NativeType("unsigned int") int flags
   ) {
      nio_uring_prep_timeout(sqe.address(), ts.address(), count, flags);
   }

   public static native void nio_uring_prep_timeout_remove(long var0, long var2, int var4);

   public static void io_uring_prep_timeout_remove(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("__u64") long user_data, @NativeType("unsigned int") int flags
   ) {
      nio_uring_prep_timeout_remove(sqe.address(), user_data, flags);
   }

   public static native void nio_uring_prep_timeout_update(long var0, long var2, long var4, int var6);

   public static void io_uring_prep_timeout_update(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      @NativeType("struct __kernel_timespec *") KernelTimespec ts,
      @NativeType("__u64") long user_data,
      @NativeType("unsigned int") int flags
   ) {
      nio_uring_prep_timeout_update(sqe.address(), ts.address(), user_data, flags);
   }

   public static native void nio_uring_prep_accept(long var0, int var2, long var3, long var5, int var7);

   public static void io_uring_prep_accept(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int fd,
      @NativeType("struct sockaddr *") Sockaddr addr,
      @NativeType("socklen_t *") IntBuffer addrlen,
      int flags
   ) {
      if (Checks.CHECKS) {
         Checks.check(addrlen, 1);
      }

      nio_uring_prep_accept(sqe.address(), fd, addr.address(), MemoryUtil.memAddress(addrlen), flags);
   }

   public static native void nio_uring_prep_accept_direct(long var0, int var2, long var3, long var5, int var7, int var8);

   public static void io_uring_prep_accept_direct(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int fd,
      @NativeType("struct sockaddr *") Sockaddr addr,
      @NativeType("socklen_t *") IntBuffer addrlen,
      int flags,
      @NativeType("unsigned int") int file_index
   ) {
      if (Checks.CHECKS) {
         Checks.check(addrlen, 1);
      }

      nio_uring_prep_accept_direct(sqe.address(), fd, addr.address(), MemoryUtil.memAddress(addrlen), flags, file_index);
   }

   public static native void nio_uring_prep_cancel(long var0, long var2, int var4);

   public static void io_uring_prep_cancel(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("__u64") long user_data, int flags) {
      nio_uring_prep_cancel(sqe.address(), user_data, flags);
   }

   public static native void nio_uring_prep_link_timeout(long var0, long var2, int var4);

   public static void io_uring_prep_link_timeout(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("struct __kernel_timespec *") KernelTimespec ts, @NativeType("unsigned int") int flags
   ) {
      nio_uring_prep_link_timeout(sqe.address(), ts.address(), flags);
   }

   public static native void nio_uring_prep_connect(long var0, int var2, long var3, int var5);

   public static void io_uring_prep_connect(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("struct sockaddr const *") Sockaddr addr, @NativeType("socklen_t") int addrlen
   ) {
      nio_uring_prep_connect(sqe.address(), fd, addr.address(), addrlen);
   }

   public static native void nio_uring_prep_files_update(long var0, long var2, int var4, int var5);

   public static void io_uring_prep_files_update(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("int *") IntBuffer fds, int offset) {
      nio_uring_prep_files_update(sqe.address(), MemoryUtil.memAddress(fds), fds.remaining(), offset);
   }

   public static native void nio_uring_prep_fallocate(long var0, int var2, int var3, long var4, long var6);

   public static void io_uring_prep_fallocate(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, int mode, @NativeType("off_t") long offset, @NativeType("off_t") long len
   ) {
      nio_uring_prep_fallocate(sqe.address(), fd, mode, offset, len);
   }

   public static native void nio_uring_prep_openat(long var0, int var2, long var3, int var5, int var6);

   public static void io_uring_prep_openat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int dfd, @NativeType("char const *") ByteBuffer path, int flags, int mode
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(path);
      }

      nio_uring_prep_openat(sqe.address(), dfd, MemoryUtil.memAddress(path), flags, mode);
   }

   public static void io_uring_prep_openat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int dfd, @NativeType("char const *") CharSequence path, int flags, int mode
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(path, true);
         long pathEncoded = stack.getPointerAddress();
         nio_uring_prep_openat(sqe.address(), dfd, pathEncoded, flags, mode);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nio_uring_prep_openat_direct(long var0, int var2, long var3, int var5, int var6, int var7);

   public static void io_uring_prep_openat_direct(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int dfd,
      @NativeType("char const *") ByteBuffer path,
      int flags,
      int mode,
      @NativeType("unsigned int") int file_index
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(path);
      }

      nio_uring_prep_openat_direct(sqe.address(), dfd, MemoryUtil.memAddress(path), flags, mode, file_index);
   }

   public static void io_uring_prep_openat_direct(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int dfd,
      @NativeType("char const *") CharSequence path,
      int flags,
      int mode,
      @NativeType("unsigned int") int file_index
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(path, true);
         long pathEncoded = stack.getPointerAddress();
         nio_uring_prep_openat_direct(sqe.address(), dfd, pathEncoded, flags, mode, file_index);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nio_uring_prep_close(long var0, int var2);

   public static void io_uring_prep_close(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd) {
      nio_uring_prep_close(sqe.address(), fd);
   }

   public static native void nio_uring_prep_close_direct(long var0, int var2);

   public static void io_uring_prep_close_direct(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("unsigned int") int file_index) {
      nio_uring_prep_close_direct(sqe.address(), file_index);
   }

   public static native void nio_uring_prep_read(long var0, int var2, long var3, int var5, int var6);

   public static void io_uring_prep_read(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("void *") ByteBuffer buf, int offset) {
      nio_uring_prep_read(sqe.address(), fd, MemoryUtil.memAddress(buf), buf.remaining(), offset);
   }

   public static native void nio_uring_prep_write(long var0, int var2, long var3, int var5, int var6);

   public static void io_uring_prep_write(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("void const *") ByteBuffer buf, int offset) {
      nio_uring_prep_write(sqe.address(), fd, MemoryUtil.memAddress(buf), buf.remaining(), offset);
   }

   public static native void nio_uring_prep_statx(long var0, int var2, long var3, int var5, int var6, long var7);

   public static void io_uring_prep_statx(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int dfd,
      @NativeType("char const *") ByteBuffer path,
      int flags,
      @NativeType("unsigned int") int mask,
      @NativeType("struct statx *") Statx statxbuf
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(path);
      }

      nio_uring_prep_statx(sqe.address(), dfd, MemoryUtil.memAddress(path), flags, mask, statxbuf.address());
   }

   public static void io_uring_prep_statx(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int dfd,
      @NativeType("char const *") CharSequence path,
      int flags,
      @NativeType("unsigned int") int mask,
      @NativeType("struct statx *") Statx statxbuf
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(path, true);
         long pathEncoded = stack.getPointerAddress();
         nio_uring_prep_statx(sqe.address(), dfd, pathEncoded, flags, mask, statxbuf.address());
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nio_uring_prep_fadvise(long var0, int var2, int var3, long var4, int var6);

   public static void io_uring_prep_fadvise(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, int offset, @NativeType("off_t") long len, int advice) {
      nio_uring_prep_fadvise(sqe.address(), fd, offset, len, advice);
   }

   public static native void nio_uring_prep_madvise(long var0, long var2, long var4, int var6);

   public static void io_uring_prep_madvise(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("void *") ByteBuffer addr, int advice) {
      nio_uring_prep_madvise(sqe.address(), MemoryUtil.memAddress(addr), addr.remaining(), advice);
   }

   public static native void nio_uring_prep_send(long var0, int var2, long var3, long var5, int var7);

   public static void io_uring_prep_send(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int sockfd, @NativeType("void const *") ByteBuffer buf, int flags) {
      nio_uring_prep_send(sqe.address(), sockfd, MemoryUtil.memAddress(buf), buf.remaining(), flags);
   }

   public static native void nio_uring_prep_recv(long var0, int var2, long var3, long var5, int var7);

   public static void io_uring_prep_recv(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int sockfd, @NativeType("void *") ByteBuffer buf, int flags) {
      nio_uring_prep_recv(sqe.address(), sockfd, MemoryUtil.memAddress(buf), buf.remaining(), flags);
   }

   public static native void nio_uring_prep_openat2(long var0, int var2, long var3, long var5);

   public static void io_uring_prep_openat2(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int dfd, @NativeType("char const *") ByteBuffer path, @NativeType("struct open_how *") OpenHow how
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(path);
      }

      nio_uring_prep_openat2(sqe.address(), dfd, MemoryUtil.memAddress(path), how.address());
   }

   public static void io_uring_prep_openat2(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int dfd, @NativeType("char const *") CharSequence path, @NativeType("struct open_how *") OpenHow how
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(path, true);
         long pathEncoded = stack.getPointerAddress();
         nio_uring_prep_openat2(sqe.address(), dfd, pathEncoded, how.address());
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nio_uring_prep_openat2_direct(long var0, int var2, long var3, long var5, int var7);

   public static void io_uring_prep_openat2_direct(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int dfd,
      @NativeType("char const *") ByteBuffer path,
      @NativeType("struct open_how *") OpenHow how,
      @NativeType("unsigned int") int file_index
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(path);
      }

      nio_uring_prep_openat2_direct(sqe.address(), dfd, MemoryUtil.memAddress(path), how.address(), file_index);
   }

   public static void io_uring_prep_openat2_direct(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int dfd,
      @NativeType("char const *") CharSequence path,
      @NativeType("struct open_how *") OpenHow how,
      @NativeType("unsigned int") int file_index
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(path, true);
         long pathEncoded = stack.getPointerAddress();
         nio_uring_prep_openat2_direct(sqe.address(), dfd, pathEncoded, how.address(), file_index);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nio_uring_prep_epoll_ctl(long var0, int var2, int var3, int var4, long var5);

   public static void io_uring_prep_epoll_ctl(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int epfd, int fd, int op, @NativeType("struct epoll_event *") EpollEvent ev
   ) {
      nio_uring_prep_epoll_ctl(sqe.address(), epfd, fd, op, ev.address());
   }

   public static native void nio_uring_prep_provide_buffers(long var0, long var2, int var4, int var5, int var6, int var7);

   public static void io_uring_prep_provide_buffers(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("void *") ByteBuffer addr, int nr, int bgid, int bid
   ) {
      nio_uring_prep_provide_buffers(sqe.address(), MemoryUtil.memAddress(addr), addr.remaining(), nr, bgid, bid);
   }

   public static native void nio_uring_prep_remove_buffers(long var0, int var2, int var3);

   public static void io_uring_prep_remove_buffers(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int nr, int bgid) {
      nio_uring_prep_remove_buffers(sqe.address(), nr, bgid);
   }

   public static native void nio_uring_prep_shutdown(long var0, int var2, int var3);

   public static void io_uring_prep_shutdown(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, int how) {
      nio_uring_prep_shutdown(sqe.address(), fd, how);
   }

   public static native void nio_uring_prep_unlinkat(long var0, int var2, long var3, int var5);

   public static void io_uring_prep_unlinkat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int dfd, @NativeType("char const *") ByteBuffer path, int flags
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(path);
      }

      nio_uring_prep_unlinkat(sqe.address(), dfd, MemoryUtil.memAddress(path), flags);
   }

   public static void io_uring_prep_unlinkat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int dfd, @NativeType("char const *") CharSequence path, int flags
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(path, true);
         long pathEncoded = stack.getPointerAddress();
         nio_uring_prep_unlinkat(sqe.address(), dfd, pathEncoded, flags);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nio_uring_prep_renameat(long var0, int var2, long var3, int var5, long var6, int var8);

   public static void io_uring_prep_renameat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int olddfd,
      @NativeType("char const *") ByteBuffer oldpath,
      int newdfd,
      @NativeType("char const *") ByteBuffer newpath,
      int flags
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(oldpath);
         Checks.checkNT1(newpath);
      }

      nio_uring_prep_renameat(sqe.address(), olddfd, MemoryUtil.memAddress(oldpath), newdfd, MemoryUtil.memAddress(newpath), flags);
   }

   public static void io_uring_prep_renameat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int olddfd,
      @NativeType("char const *") CharSequence oldpath,
      int newdfd,
      @NativeType("char const *") CharSequence newpath,
      int flags
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(oldpath, true);
         long oldpathEncoded = stack.getPointerAddress();
         stack.nUTF8(newpath, true);
         long newpathEncoded = stack.getPointerAddress();
         nio_uring_prep_renameat(sqe.address(), olddfd, oldpathEncoded, newdfd, newpathEncoded, flags);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nio_uring_prep_sync_file_range(long var0, int var2, int var3, int var4, int var5);

   public static void io_uring_prep_sync_file_range(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int fd, @NativeType("unsigned int") int len, int offset, int flags
   ) {
      nio_uring_prep_sync_file_range(sqe.address(), fd, len, offset, flags);
   }

   public static native void nio_uring_prep_mkdirat(long var0, int var2, long var3, int var5);

   public static void io_uring_prep_mkdirat(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, int dfd, @NativeType("char const *") ByteBuffer path, int mode) {
      if (Checks.CHECKS) {
         Checks.checkNT1(path);
      }

      nio_uring_prep_mkdirat(sqe.address(), dfd, MemoryUtil.memAddress(path), mode);
   }

   public static void io_uring_prep_mkdirat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe, int dfd, @NativeType("char const *") CharSequence path, int mode
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(path, true);
         long pathEncoded = stack.getPointerAddress();
         nio_uring_prep_mkdirat(sqe.address(), dfd, pathEncoded, mode);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nio_uring_prep_symlinkat(long var0, long var2, int var4, long var5);

   public static void io_uring_prep_symlinkat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      @NativeType("char const *") ByteBuffer target,
      int newdirfd,
      @NativeType("char const *") ByteBuffer linkpath
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(target);
         Checks.checkNT1(linkpath);
      }

      nio_uring_prep_symlinkat(sqe.address(), MemoryUtil.memAddress(target), newdirfd, MemoryUtil.memAddress(linkpath));
   }

   public static void io_uring_prep_symlinkat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      @NativeType("char const *") CharSequence target,
      int newdirfd,
      @NativeType("char const *") CharSequence linkpath
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(target, true);
         long targetEncoded = stack.getPointerAddress();
         stack.nUTF8(linkpath, true);
         long linkpathEncoded = stack.getPointerAddress();
         nio_uring_prep_symlinkat(sqe.address(), targetEncoded, newdirfd, linkpathEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nio_uring_prep_linkat(long var0, int var2, long var3, int var5, long var6, int var8);

   public static void io_uring_prep_linkat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int olddfd,
      @NativeType("char const *") ByteBuffer oldpath,
      int newdfd,
      @NativeType("char const *") ByteBuffer newpath,
      int flags
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(oldpath);
         Checks.checkNT1(newpath);
      }

      nio_uring_prep_linkat(sqe.address(), olddfd, MemoryUtil.memAddress(oldpath), newdfd, MemoryUtil.memAddress(newpath), flags);
   }

   public static void io_uring_prep_linkat(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int olddfd,
      @NativeType("char const *") CharSequence oldpath,
      int newdfd,
      @NativeType("char const *") CharSequence newpath,
      int flags
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(oldpath, true);
         long oldpathEncoded = stack.getPointerAddress();
         stack.nUTF8(newpath, true);
         long newpathEncoded = stack.getPointerAddress();
         nio_uring_prep_linkat(sqe.address(), olddfd, oldpathEncoded, newdfd, newpathEncoded, flags);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native int nio_uring_sq_ready(long var0);

   @NativeType("unsigned int")
   public static int io_uring_sq_ready(@NativeType("struct io_uring const *") IOURing ring) {
      if (Checks.CHECKS) {
         IOURing.validate(ring.address());
      }

      return nio_uring_sq_ready(ring.address());
   }

   public static native int nio_uring_sq_space_left(long var0);

   @NativeType("unsigned int")
   public static int io_uring_sq_space_left(@NativeType("struct io_uring const *") IOURing ring) {
      if (Checks.CHECKS) {
         IOURing.validate(ring.address());
      }

      return nio_uring_sq_space_left(ring.address());
   }

   public static native int nio_uring_sqring_wait(long var0);

   public static int io_uring_sqring_wait(@NativeType("struct io_uring *") IOURing ring) {
      return nio_uring_sqring_wait(ring.address());
   }

   public static native int nio_uring_cq_ready(long var0);

   @NativeType("unsigned int")
   public static int io_uring_cq_ready(@NativeType("struct io_uring const *") IOURing ring) {
      if (Checks.CHECKS) {
         IOURing.validate(ring.address());
      }

      return nio_uring_cq_ready(ring.address());
   }

   public static native boolean nio_uring_cq_eventfd_enabled(long var0);

   @NativeType("bool")
   public static boolean io_uring_cq_eventfd_enabled(@NativeType("struct io_uring const *") IOURing ring) {
      if (Checks.CHECKS) {
         IOURing.validate(ring.address());
      }

      return nio_uring_cq_eventfd_enabled(ring.address());
   }

   public static native int nio_uring_cq_eventfd_toggle(long var0, boolean var2);

   public static int io_uring_cq_eventfd_toggle(@NativeType("struct io_uring *") IOURing ring, @NativeType("bool") boolean enabled) {
      return nio_uring_cq_eventfd_toggle(ring.address(), enabled);
   }

   public static native int nio_uring_wait_cqe_nr(long var0, long var2, int var4);

   public static int io_uring_wait_cqe_nr(@NativeType("struct io_uring *") IOURing ring, @NativeType("struct io_uring_cqe **") PointerBuffer cqe_ptr) {
      return nio_uring_wait_cqe_nr(ring.address(), MemoryUtil.memAddress(cqe_ptr), cqe_ptr.remaining());
   }

   public static native int nio_uring_peek_cqe(long var0, long var2);

   public static int io_uring_peek_cqe(@NativeType("struct io_uring *") IOURing ring, @NativeType("struct io_uring_cqe **") PointerBuffer cqe_ptr) {
      if (Checks.CHECKS) {
         Checks.check(cqe_ptr, 1);
      }

      return nio_uring_peek_cqe(ring.address(), MemoryUtil.memAddress(cqe_ptr));
   }

   public static native int nio_uring_wait_cqe(long var0, long var2);

   public static int io_uring_wait_cqe(@NativeType("struct io_uring *") IOURing ring, @NativeType("struct io_uring_cqe **") PointerBuffer cqe_ptr) {
      if (Checks.CHECKS) {
         Checks.check(cqe_ptr, 1);
      }

      return nio_uring_wait_cqe(ring.address(), MemoryUtil.memAddress(cqe_ptr));
   }

   public static native int io_uring_mlock_size(@NativeType("unsigned") int var0, @NativeType("unsigned") int var1);

   public static native int nio_uring_mlock_size_params(int var0, long var1);

   public static int io_uring_mlock_size_params(@NativeType("unsigned") int entries, @NativeType("struct io_uring_params *") IOURingParams p) {
      return nio_uring_mlock_size_params(entries, p.address());
   }

   public static native int nio_uring_register_buffers_tags(long var0, long var2, long[] var4, int var5);

   public static int io_uring_register_buffers_tags(
      @NativeType("struct io_uring *") IOURing ring, @NativeType("struct iovec const *") IOVec.Buffer iovecs, @NativeType("__u64 const *") long[] tags
   ) {
      if (Checks.CHECKS) {
         Checks.check(tags, iovecs.remaining());
      }

      return nio_uring_register_buffers_tags(ring.address(), iovecs.address(), tags, iovecs.remaining());
   }

   public static native int nio_uring_register_buffers_update_tag(long var0, int var2, long var3, long[] var5, int var6);

   public static int io_uring_register_buffers_update_tag(
      @NativeType("struct io_uring *") IOURing ring,
      @NativeType("unsigned") int off,
      @NativeType("struct iovec const *") IOVec.Buffer iovecs,
      @NativeType("__u64 const *") long[] tags
   ) {
      if (Checks.CHECKS) {
         Checks.check(tags, iovecs.remaining());
      }

      return nio_uring_register_buffers_update_tag(ring.address(), off, iovecs.address(), tags, iovecs.remaining());
   }

   public static native int nio_uring_register_files(long var0, int[] var2, int var3);

   public static int io_uring_register_files(@NativeType("struct io_uring *") IOURing ring, @NativeType("int const *") int[] files) {
      return nio_uring_register_files(ring.address(), files, files.length);
   }

   public static native int nio_uring_register_files_tags(long var0, int[] var2, long[] var3, int var4);

   public static int io_uring_register_files_tags(
      @NativeType("struct io_uring *") IOURing ring, @NativeType("int const *") int[] files, @NativeType("__u64 const *") long[] tags
   ) {
      if (Checks.CHECKS) {
         Checks.check(tags, files.length);
      }

      return nio_uring_register_files_tags(ring.address(), files, tags, files.length);
   }

   public static native int nio_uring_register_files_update_tag(long var0, int var2, int[] var3, long[] var4, int var5);

   public static int io_uring_register_files_update_tag(
      @NativeType("struct io_uring *") IOURing ring,
      @NativeType("unsigned") int off,
      @NativeType("int const *") int[] files,
      @NativeType("__u64 const *") long[] tags
   ) {
      if (Checks.CHECKS) {
         Checks.check(tags, files.length);
      }

      return nio_uring_register_files_update_tag(ring.address(), off, files, tags, files.length);
   }

   public static native int nio_uring_register_files_update(long var0, int var2, int[] var3, int var4);

   public static int io_uring_register_files_update(
      @NativeType("struct io_uring *") IOURing ring, @NativeType("unsigned") int off, @NativeType("int *") int[] files
   ) {
      return nio_uring_register_files_update(ring.address(), off, files, files.length);
   }

   public static native int nio_uring_register_iowq_max_workers(long var0, int[] var2);

   public static int io_uring_register_iowq_max_workers(@NativeType("struct io_uring *") IOURing ring, @NativeType("unsigned int *") int[] values) {
      if (Checks.CHECKS) {
         Checks.check(values, 2);
      }

      return nio_uring_register_iowq_max_workers(ring.address(), values);
   }

   public static native void nio_uring_prep_accept(long var0, int var2, long var3, int[] var5, int var6);

   public static void io_uring_prep_accept(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int fd,
      @NativeType("struct sockaddr *") Sockaddr addr,
      @NativeType("socklen_t *") int[] addrlen,
      int flags
   ) {
      if (Checks.CHECKS) {
         Checks.check(addrlen, 1);
      }

      nio_uring_prep_accept(sqe.address(), fd, addr.address(), addrlen, flags);
   }

   public static native void nio_uring_prep_accept_direct(long var0, int var2, long var3, int[] var5, int var6, int var7);

   public static void io_uring_prep_accept_direct(
      @NativeType("struct io_uring_sqe *") IOURingSQE sqe,
      int fd,
      @NativeType("struct sockaddr *") Sockaddr addr,
      @NativeType("socklen_t *") int[] addrlen,
      int flags,
      @NativeType("unsigned int") int file_index
   ) {
      if (Checks.CHECKS) {
         Checks.check(addrlen, 1);
      }

      nio_uring_prep_accept_direct(sqe.address(), fd, addr.address(), addrlen, flags, file_index);
   }

   public static native void nio_uring_prep_files_update(long var0, int[] var2, int var3, int var4);

   public static void io_uring_prep_files_update(@NativeType("struct io_uring_sqe *") IOURingSQE sqe, @NativeType("int *") int[] fds, int offset) {
      nio_uring_prep_files_update(sqe.address(), fds, fds.length, offset);
   }

   static {
      Library.initialize();
   }
}
