	.file	"6.cpp"
	.intel_syntax noprefix
	.text
	.section	.text$_ZN4BaseC2Ev,"x"
	.linkonce discard
	.align 2
	.globl	_ZN4BaseC2Ev
	.def	_ZN4BaseC2Ev;	.scl	2;	.type	32;	.endef
	.seh_proc	_ZN4BaseC2Ev
_ZN4BaseC2Ev:
.LFB29:
	push	rbp
	.seh_pushreg	rbp
	mov	rbp, rsp
	.seh_setframe	rbp, 0
	sub	rsp, 32
	.seh_stackalloc	32
	.seh_endprologue
	mov	QWORD PTR 16[rbp], rcx
	lea	rdx, _ZTV4Base[rip+16]
	mov	rax, QWORD PTR 16[rbp]
	mov	QWORD PTR [rax], rdx
	mov	rcx, QWORD PTR 16[rbp]
	call	_ZN4Base6metodaEv
	nop
	add	rsp, 32
	pop	rbp
	ret
	.seh_endproc
	.section .rdata,"dr"
.LC0:
	.ascii "ja sam bazna implementacija!\0"
	.section	.text$_ZN4Base15virtualnaMetodaEv,"x"
	.linkonce discard
	.align 2
	.globl	_ZN4Base15virtualnaMetodaEv
	.def	_ZN4Base15virtualnaMetodaEv;	.scl	2;	.type	32;	.endef
	.seh_proc	_ZN4Base15virtualnaMetodaEv
_ZN4Base15virtualnaMetodaEv:
.LFB31:
	push	rbp
	.seh_pushreg	rbp
	mov	rbp, rsp
	.seh_setframe	rbp, 0
	sub	rsp, 32
	.seh_stackalloc	32
	.seh_endprologue
	mov	QWORD PTR 16[rbp], rcx
	lea	rcx, .LC0[rip]
	call	puts
	nop
	add	rsp, 32
	pop	rbp
	ret
	.seh_endproc
	.section .rdata,"dr"
.LC1:
	.ascii "Metoda kaze: \0"
	.section	.text$_ZN4Base6metodaEv,"x"
	.linkonce discard
	.align 2
	.globl	_ZN4Base6metodaEv
	.def	_ZN4Base6metodaEv;	.scl	2;	.type	32;	.endef
	.seh_proc	_ZN4Base6metodaEv
_ZN4Base6metodaEv:
.LFB32:
	push	rbp
	.seh_pushreg	rbp
	mov	rbp, rsp
	.seh_setframe	rbp, 0
	sub	rsp, 32
	.seh_stackalloc	32
	.seh_endprologue
	mov	QWORD PTR 16[rbp], rcx
	lea	rcx, .LC1[rip]
	call	printf
	mov	rax, QWORD PTR 16[rbp]
	mov	rax, QWORD PTR [rax]
	mov	rax, QWORD PTR [rax]
	mov	rcx, QWORD PTR 16[rbp]
	call	rax
	nop
	add	rsp, 32
	pop	rbp
	ret
	.seh_endproc
	.section	.text$_ZN7DerivedC1Ev,"x"
	.linkonce discard
	.align 2
	.globl	_ZN7DerivedC1Ev
	.def	_ZN7DerivedC1Ev;	.scl	2;	.type	32;	.endef
	.seh_proc	_ZN7DerivedC1Ev
_ZN7DerivedC1Ev:
.LFB35:
	push	rbp
	.seh_pushreg	rbp
	mov	rbp, rsp
	.seh_setframe	rbp, 0
	sub	rsp, 32
	.seh_stackalloc	32
	.seh_endprologue
	mov	QWORD PTR 16[rbp], rcx
	mov	rax, QWORD PTR 16[rbp]
	mov	rcx, rax
	call	_ZN4BaseC2Ev
	lea	rdx, _ZTV7Derived[rip+16]
	mov	rax, QWORD PTR 16[rbp]
	mov	QWORD PTR [rax], rdx
	mov	rax, QWORD PTR 16[rbp]
	mov	rcx, rax
	call	_ZN4Base6metodaEv
	nop
	add	rsp, 32
	pop	rbp
	ret
	.seh_endproc
	.section .rdata,"dr"
	.align 8
.LC2:
	.ascii "ja sam izvedena implementacija!\0"
	.section	.text$_ZN7Derived15virtualnaMetodaEv,"x"
	.linkonce discard
	.align 2
	.globl	_ZN7Derived15virtualnaMetodaEv
	.def	_ZN7Derived15virtualnaMetodaEv;	.scl	2;	.type	32;	.endef
	.seh_proc	_ZN7Derived15virtualnaMetodaEv
_ZN7Derived15virtualnaMetodaEv:
.LFB36:
	push	rbp
	.seh_pushreg	rbp
	mov	rbp, rsp
	.seh_setframe	rbp, 0
	sub	rsp, 32
	.seh_stackalloc	32
	.seh_endprologue
	mov	QWORD PTR 16[rbp], rcx
	lea	rcx, .LC2[rip]
	call	puts
	nop
	add	rsp, 32
	pop	rbp
	ret
	.seh_endproc
	.def	__main;	.scl	2;	.type	32;	.endef
	.text
	.globl	main
	.def	main;	.scl	2;	.type	32;	.endef
	.seh_proc	main
main:
.LFB37:
	push	rbp
	.seh_pushreg	rbp
	push	rsi
	.seh_pushreg	rsi
	push	rbx
	.seh_pushreg	rbx
	mov	rbp, rsp
	.seh_setframe	rbp, 0
	sub	rsp, 48
	.seh_stackalloc	48
	.seh_endprologue
	call	__main
	mov	ecx, 8
.LEHB0:
	call	_Znwy
.LEHE0:
	mov	rbx, rax
	mov	rcx, rbx
.LEHB1:
	call	_ZN7DerivedC1Ev
.LEHE1:
	mov	QWORD PTR -8[rbp], rbx
	mov	rax, QWORD PTR -8[rbp]
	mov	rcx, rax
.LEHB2:
	call	_ZN4Base6metodaEv
	mov	eax, 0
	jmp	.L10
.L9:
	mov	rsi, rax
	mov	edx, 8
	mov	rcx, rbx
	call	_ZdlPvy
	mov	rax, rsi
	mov	rcx, rax
	call	_Unwind_Resume
.LEHE2:
.L10:
	add	rsp, 48
	pop	rbx
	pop	rsi
	pop	rbp
	ret
	.def	__gxx_personality_seh0;	.scl	2;	.type	32;	.endef
	.seh_handler	__gxx_personality_seh0, @unwind, @except
	.seh_handlerdata
.LLSDA37:
	.byte	0xff
	.byte	0xff
	.byte	0x1
	.uleb128 .LLSDACSE37-.LLSDACSB37
.LLSDACSB37:
	.uleb128 .LEHB0-.LFB37
	.uleb128 .LEHE0-.LEHB0
	.uleb128 0
	.uleb128 0
	.uleb128 .LEHB1-.LFB37
	.uleb128 .LEHE1-.LEHB1
	.uleb128 .L9-.LFB37
	.uleb128 0
	.uleb128 .LEHB2-.LFB37
	.uleb128 .LEHE2-.LEHB2
	.uleb128 0
	.uleb128 0
.LLSDACSE37:
	.text
	.seh_endproc
	.globl	_ZTV7Derived
	.section	.rdata$_ZTV7Derived,"dr"
	.linkonce same_size
	.align 8
_ZTV7Derived:
	.quad	0
	.quad	_ZTI7Derived
	.quad	_ZN7Derived15virtualnaMetodaEv
	.globl	_ZTV4Base
	.section	.rdata$_ZTV4Base,"dr"
	.linkonce same_size
	.align 8
_ZTV4Base:
	.quad	0
	.quad	_ZTI4Base
	.quad	_ZN4Base15virtualnaMetodaEv
	.globl	_ZTI7Derived
	.section	.rdata$_ZTI7Derived,"dr"
	.linkonce same_size
	.align 8
_ZTI7Derived:
	.quad	_ZTVN10__cxxabiv120__si_class_type_infoE+16
	.quad	_ZTS7Derived
	.quad	_ZTI4Base
	.globl	_ZTS7Derived
	.section	.rdata$_ZTS7Derived,"dr"
	.linkonce same_size
	.align 8
_ZTS7Derived:
	.ascii "7Derived\0"
	.globl	_ZTI4Base
	.section	.rdata$_ZTI4Base,"dr"
	.linkonce same_size
	.align 8
_ZTI4Base:
	.quad	_ZTVN10__cxxabiv117__class_type_infoE+16
	.quad	_ZTS4Base
	.globl	_ZTS4Base
	.section	.rdata$_ZTS4Base,"dr"
	.linkonce same_size
_ZTS4Base:
	.ascii "4Base\0"
	.ident	"GCC: (x86_64-win32-seh-rev0, Built by MinGW-W64 project) 8.1.0"
	.def	puts;	.scl	2;	.type	32;	.endef
	.def	printf;	.scl	2;	.type	32;	.endef
	.def	_Znwy;	.scl	2;	.type	32;	.endef
	.def	_ZdlPvy;	.scl	2;	.type	32;	.endef
	.def	_Unwind_Resume;	.scl	2;	.type	32;	.endef
