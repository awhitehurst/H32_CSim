!o
; functions
increment$_i:
	esba
	ldr 2
	addc 1
	str 2
	ldr 2
	ret
	reba
	ret
	extern develop$_i_c_i
boing$_i:
	esba
	ldc 'CC'
	ret
	reba
	ret
@temp:	dw 0
main$:
	ldc 9
	push ; u
	ldc 6
	push ; v
	ldc 'c'
	push ; y
	ldr -2
	str -1
	ldr -3
	push
	call increment$_c
	dloc 1
; Loop
@L000:
	ldr -2
	push
	ldr -1
	scmp
	addc 1
	jz @L001
	ldc 0
	ja *+2
@L001:
	ldc 1
	jz @L002
	ldc 9
	push ; t
	dloc 1
	ja @L000
@L002:
	ldr -2
	push
	call increment$_i
	dloc 1
	ldr -1
	push
	ldr -3
	push
	ldr -2
	push
	call develop$_i_c_i
	dloc 3
	ldr -2
	push
	call develop$_i
	dloc 1
	ldr -2
	push
	ldr -2
	push
	ldr -2
	push
	ldr -2
	push
	ldr -2
	push
	ldr -2
	push
	ldr -2
	push
	ldr -3
	push
	call increment$_c_i_i_i_i_i_i_i
	dloc 8
	dloc 3
	halt
	end main$
