import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const PieChartTopSale = () => {
  // Dữ liệu test
  const categories = [
    { category: 'Danh mục 1' },
    { category: 'Danh mục 2' },
    { category: 'Danh mục 3' },
    { category: 'Danh mục 4' }
  ];

  const revenues = [
    { category: 'Danh mục 1', quantity: 150 },
    { category: 'Danh mục 2', quantity: 200 },
    { category: 'Danh mục 3', quantity: 80 },
    { category: 'Danh mục 4', quantity: 300 }
  ];

  const dataPie = categories.map(e => {
    const categoryRevenue = revenues.find(r => r.category === e.category);
    return {
      name: e.category,
      value: categoryRevenue ? categoryRevenue.quantity : 0
    };
  });

  const generateColors = (num) => {
    const colors = new Set();
    while (colors.size < num) {
      const color = `#${Math.floor(Math.random() * 16777215).toString(16).padStart(6, '0')}`; // Tạo màu HEX
      colors.add(color);
    }
    return Array.from(colors);
  };

  const COLORS = generateColors(dataPie.length);

  return (
    <div className="pie-chart">
      <h3 className='pie-chart__label'>Danh mục bán chạy</h3>
      <ResponsiveContainer width={'100%'} height={'100%'}>
          <PieChart>
            <Pie
              data={dataPie}
              cx="50%"
              cy="50%"
              innerRadius={80}
              outerRadius={120}
              fill="#8884d8"
              paddingAngle={6}
              dataKey="value"
            >
              {dataPie.map((entry, index) => (
                <Cell 
                  key={`cell-${index}`} 
                  fill={COLORS[index % COLORS.length]} 
                  style={{cursor: 'pointer'}}
                />
              ))}
            </Pie>
            <Tooltip formatter={(value, name) => [`${value} lượt bán`, `${name}`]} />
            <Legend />
          </PieChart>
        </ResponsiveContainer>
    </div>
  );
};

export default PieChartTopSale;
