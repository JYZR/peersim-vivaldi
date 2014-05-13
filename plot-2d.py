from matplotlib import pyplot
from matplotlib import animation
import numpy

cycles = 1000
scale = 100

def coordinates_filename(i):
    return './vivaldi-tmp/coordinates' + `i` + '.csv'
def meta_filename(i):
    return './vivaldi-tmp/meta' + `i` + '.csv'

fig = pyplot.figure()
# fig.set_size_inches(16,9)
ax = pyplot.axes(xlim=(-scale, scale), ylim=(-scale, scale))
sc = ax.scatter([],[])
text_cycle = pyplot.text(-0.9*scale, 0.9*scale, "Cycle: ")
text_sum = pyplot.text(-0.9*scale, 0.85*scale, "Sum of errors: ")
text_avg_err = pyplot.text(-0.9*scale, 0.8*scale, "Average error: ")

text_avg_uncertainty = pyplot.text(-0.9*scale, -0.8*scale, "Average uncertainty: ")
text_avg_uncertainty_balance = pyplot.text(-0.9*scale, -0.85*scale, "Average uncertainty balance: ")
text_avg_move_distance = pyplot.text(-0.9*scale, -0.9*scale, "Average move distance: ")


def init():
    sc = ax.scatter([],[])
    return sc

def animate(i):
    file = open ( coordinates_filename(i) , 'r' )
    # Fetch a list of coordinates [[x1,y1], [x2,y2] ...]
    coords = [ map( float, line.split(',') ) for line in file ]
    file.close()
    # Separate into two lists [x1, x2 ...] and [y1, y2 ...]
    #[x, y] = map( list, zip( *coords ) )
    sc.set_offsets(coords)
    # sc.set_facecolors(["b","g","r","c","m"]*200)

    text_cycle.set_text("Cycle: " + `i`)
    file = open ( meta_filename(i) , 'r' )
    meta = [ line.split(':') for line in file ]
    text_sum.set_text("Sum of errors: " + `float(meta[0][1])`)
    text_avg_err.set_text("Average error: " + `float(meta[1][1])`)
    text_avg_uncertainty.set_text("Average uncertainty: " + `float(meta[2][1])`)
    text_avg_uncertainty_balance.set_text("Average uncertainty balance: " + `float(meta[3][1])`)
    text_avg_move_distance.set_text("Average move distance: " + `float(meta[4][1])`)
    file.close()

anim = animation.FuncAnimation(fig, animate, xrange(cycles), init_func=init, interval=100, repeat=False)

pyplot.show()

# anim.save('basic_animation.mp4', extra_args=['-vcodec', 'libx264'], dpi=120)

"""
anim.save('basic_animation.mp4', fps=1, extra_args=['-vcodec', 'libx264'])
pyplot.ylabel('some numbers')
pyplot.savefig('test.pdf')

c = numpy.random.random(9)
sc.set_color(c)

"""