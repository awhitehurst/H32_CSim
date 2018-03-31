!o
; functions
increment$_i:
	esba
	ldr 2
	ret
	reba
	ret
boing$_i:
	esba
	ldc 'c'
	push ; y
	ldc 8
	push
	call increment$_i
	dloc 1
	str 2
	ldr 2
	ret
	dloc 1
	reba
	ret
develop$_i_c_i:
	esba
	ldr 3
	st @temp
	ldr 2
	add @temp
	str 2
	ldr 2
	ret
	reba
	ret
returnfalse$:
	esba
	ret
	reba
	ret
@temp:	dw 0
main$:
	aloc 1 ; v
	ldc 9
	push ; u
	aloc 1 ; x
	ldc -1
	cora
	push ; pointer
	aloc 1 ; star
	ldr -1
	str -2
	ldc 9
	push
	call increment$_i
	dloc 1
; Loop
@L000:
	ldr -1
	push
	ldr -2
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
	ldr -2
	addc 1
	str -2
	dloc 1
	ja @L000
@L002:
	call returnfalse$
	ldr -1
	push
	call increment$_i
	dloc 1
	ldr -2
	push
	ldc 'c'
	push
	ldr -1
	push
	call develop$_i_c_i
	dloc 3
	dloc 5
	halt
	end main$
