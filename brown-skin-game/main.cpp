#include <iostream>
#include <windows.h>

#include <gl\GL.h>
#include <gl\GLU.h>
#include <glut-3.7.6-bin\glut.h>

void init(void);
void display(void);

int main (int argc, char **argv)
{
	// Initializing glut and creating the window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(500, 500);
	glutInitWindowPosition(400, 300);
	glutCreateWindow("My First OpenGL Application");

	init();

	// Start Drawing the house
	glutDisplayFunc(display);
	glutMainLoop();
	return 0;
}

void init(void)
{
	glClearColor(1.0, 1.0, 1.0, 0.0);
	glColor3f(1.0, 0.0, 1.0);
	glMatrixMode(GL_PROJECTION);	
	gluOrtho2D(0.0, 500, 0.0, 500);
}

void display(void)
{
    glClear (GL_COLOR_BUFFER_BIT); 
    glColor3f (0.0, 0.4, 0.2);

	//Drawing the house's main square
    glBegin (GL_LINE_LOOP);
        glVertex2i (50, 250);
        glVertex2i (450, 250);
		glVertex2i (450, 125);
		glVertex2i (50, 125);
    glEnd ( );

	//Drawing the roof
	glBegin (GL_LINE_LOOP);
		glVertex2i (50, 250);
		glVertex2i (250, 375);
		glVertex2i (450, 250);
    glEnd ( );

	//Drawing the house's chimney
	glBegin (GL_LINE_STRIP);
		glVertex2i (110, 290);
		glVertex2i (110, 450);
		glVertex2i (170, 450);
		glVertex2i (170, 330);
    glEnd ( );

	// Drawing the house's window
	glBegin (GL_LINE_LOOP);
		glVertex2i (110, 225);
		glVertex2i (170, 225);
		glVertex2i (170, 175);
		glVertex2i (110, 175);
    glEnd ( );

	// Drawing the house's window's "diagonals"
	glBegin (GL_LINES);
		glVertex2i (110, 225);
		glVertex2i (170, 175);
    glEnd ( );
	glBegin (GL_LINES);
		glVertex2i (110, 175);
		glVertex2i (170, 225);
    glEnd ( );

	// Drawing the door
	glBegin (GL_LINE_STRIP);
		glVertex2i (275, 125);
		glVertex2i (275, 200);
		glVertex2i (325, 200);
		glVertex2i (325, 125);
    glEnd ( );

    glFlush ( );
}